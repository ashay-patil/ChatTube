package com.chat.backend.RAG;

import com.google.genai.Client;
import com.google.genai.types.ContentEmbedding;
import com.google.genai.types.EmbedContentConfig;
import com.google.genai.types.EmbedContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingService {

    private final Client client;

    public EmbeddingService( @Value("${GEMINI_API_KEY}") String apiKey ) {
        this.client = Client.builder().apiKey(apiKey).build();
    }

    public List<Double> generateEmbedding(String text) {

        EmbedContentConfig config = EmbedContentConfig.builder().outputDimensionality(768).build();

        EmbedContentResponse response = client.models.embedContent(
                        "gemini-embedding-2",
                        text,
                        config
        );

        ContentEmbedding embedding = response.embeddings()
                        .orElseThrow(() ->
                                new RuntimeException("No embedding returned")
                        )
                        .get(0);

        return embedding.values()
                .orElseThrow(() ->
                        new RuntimeException("Embedding values are empty")
                )
                .stream()
                .map(Float::doubleValue)
                .toList();
    }
}