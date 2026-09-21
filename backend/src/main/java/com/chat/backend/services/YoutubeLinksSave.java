package com.chat.backend.services;

import com.chat.backend.DTO.SupadataResponse;
import com.chat.backend.DTO.Transcript;
import com.chat.backend.entities.Video;
import com.chat.backend.entities.VideoChunk;
import com.chat.backend.repositories.VideoChunkRepository;
import com.chat.backend.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class YoutubeLinksSave {

    @Value("${SUPADATA_API_KEY}")
    private String API_KEY;

    @Autowired
    private VideoRepository repository;

    @Autowired
    private VideoChunkRepository videoChunkRepository;

    @Autowired
    private EmbeddingService embeddingService;

    public String saveYoutubeLinksToDB(List<String> youtubeLinks)  {
        youtubeLinks.forEach((link)->{
            Video video = new Video(extractVideoId(link), link);
            Video savedVideo = repository.save(video);
            RestClient restClient = RestClient.create();
            SupadataResponse result =
                    restClient
                            .get()
                            .uri("https://api.supadata.ai/v1/transcript?url={link}" , link)
                            .header("x-api-key", API_KEY)
                            .header("Accept", "application/json")
                            .retrieve()
                            .body(SupadataResponse.class);
            System.out.println(result);
            if(result == null) {
                throw new RuntimeException("Supadata returned null");
            }
            List<Transcript> transcripts = result.getContent();

            int i = 0;
            int chunkIndex = 0;

            while (i < transcripts.size()) {

                StringBuilder sb = new StringBuilder();

                int startTime = transcripts.get(i).getOffset();
                int endTime = startTime;

                int j = i;

                while (j < transcripts.size() && j < i + 50) {

                    Transcript transcript = transcripts.get(j);

                    sb.append(transcript.getText()).append(" ");

                    endTime = transcript.getOffset() + transcript.getDuration();

                    j++;
                }

                String chunkText = sb.toString().trim();

                // Generate embedding for this chunk
                List<Double> embedding = embeddingService.generateEmbedding(chunkText);

                // Save everything together
                VideoChunk videoChunk = new VideoChunk(
                        savedVideo,
                        chunkIndex,
                        startTime,
                        endTime,
                        chunkText,
                        embedding
                );

                videoChunkRepository.save(videoChunk);

                chunkIndex++;
                i = j;
            }

        });
        return "Embeddings Generated and Saved to DB";
    }

    public String extractVideoId(String youtubeUrl) {
        // Regex to match standard, shortened, and embed YouTube URLs
        String pattern = "(?:https?:\\/\\/)?(?:www\\.)?(?:youtube\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youtubeUrl);

        if (!matcher.find()) {
            throw new RuntimeException("Invalid Youtube Video Id");
        }

        return matcher.group(1);
    }
}
