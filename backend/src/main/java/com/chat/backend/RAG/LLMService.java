package com.chat.backend.RAG;
import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.google.genai.types.GenerateContentResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
public class LLMService {

    private final Client client;

    public LLMService( @Value("${GEMINI_API_KEY}") String apiKey ) {
        this.client = Client.builder().apiKey(apiKey).build();
    }

    public String getGeminiResponse(String userQuestion, String videoTranscriptContext, String chatHistoryContext) throws Exception{
//        return "{\n" +
//                "  \"response\": \"Yes, the video transcript mentions that your brain is the most powerful weapon in the world.\",\n" +
//                "  \"timestamps\": [\n" +
//                "    {\n" +
//                "      \"videoId\": \"6ab0f20c5d1bb068c2df6446\",\n" +
//                "      \"startTime\": 670,\n" +
//                "      \"endTime\": 135190\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
        String promptString = this.loadPromptFromFile("prompt.txt");
        String promptContent = this.putValuesToTemplate(promptString, Map.of(
                "question", userQuestion,
                "videoTranscriptContext", videoTranscriptContext,
                "chatHistoryContext", chatHistoryContext
        ));
        System.out.println(promptContent);
        GenerateContentResponse response =
                client.models.generateContent("gemini-3.5-flash-lite", promptContent, null);

        return response.text();
    }

    private String loadPromptFromFile(String filename) throws IOException {
        Path path = new ClassPathResource(filename).getFile().toPath();
        return Files.readString(path);
    }

    private String putValuesToTemplate(String template, Map<String, String> values){
        for(Map.Entry<String, String> entry : values.entrySet()) {
            template = template.replace("{" +entry.getKey()+ "}", entry.getValue());
        }
        return template;
    }
}
