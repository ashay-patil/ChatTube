package com.chat.backend.services;

import com.chat.backend.DTO.SupadataResponse;
import com.chat.backend.DTO.Transcript;
import com.chat.backend.entities.Video;
import com.chat.backend.entities.VideoChunk;
import com.chat.backend.repositories.VideoChunkRepository;
import com.chat.backend.repositories.VideoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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


    public List<VideoChunk> saveYoutubeLinksToDB(List<String> youtubeLinks) throws Exception {
        youtubeLinks.forEach((link)->{
            Video video = new Video(extractVideoId(link), link);
            Video savedVideo = repository.save(video);
            RestTemplate restTemplate = new RestTemplate();

//            SupadataResponse result = restTemplate.getForObject("https://api.supadata.ai/v1/transcript?url=" + encodedUri, SupadataResponse.class);
//            System.out.println("Supadata result : " + result);
//            List<Transcript> transcripts = result.getTranscript();

//            if(API_KEY == null || API_KEY.length()==0) {
//                throw new RuntimeException("API_KEY not found");
//            }
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
            List<Transcript> transcripts = result.getContent();
            System.out.println(transcripts);
            int chunkIndex = 0;
            for(Transcript transcript : transcripts) {
                VideoChunk videoChunk = new VideoChunk(savedVideo, chunkIndex, transcript.getOffset(), transcript.getOffset() + transcript.getDuration(), transcript.getText());
                chunkIndex++;
                videoChunkRepository.save(videoChunk);
            };
        });
        System.out.println("Saved to DB");
        return videoChunkRepository.findAll();
    }

    public String extractVideoId(String youtubeUrl) {
        // Regex to match standard, shortened, and embed YouTube URLs
        String pattern = "(?:https?:\\/\\/)?(?:www\\.)?(?:youtube\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youtubeUrl);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "Could not extract video ID";
    }
}
