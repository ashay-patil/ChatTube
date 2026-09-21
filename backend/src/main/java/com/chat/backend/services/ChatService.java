package com.chat.backend.services;

import com.chat.backend.DTO.LLMResponse;
import com.chat.backend.DTO.VideoChunkSearchResult;
import com.chat.backend.entities.ChatHistory;
import com.chat.backend.repositories.ChatHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    VideoChunkSimilaritySearch videoChunkSimilaritySearch;

    @Autowired
    EmbeddingService embeddingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    LLMService llmService;

    @Autowired
    ChatHistoryRepository chatHistoryRepository;

    public LLMResponse getResponse(String userQuestion) throws Exception{
        List<Double> userQuestionEmbedding = embeddingService.generateEmbedding(userQuestion);
        List<VideoChunkSearchResult> knowledgeBaseSimilarityResult = videoChunkSimilaritySearch.search(userQuestionEmbedding);

        StringBuilder promptContext = new StringBuilder("");
        knowledgeBaseSimilarityResult.forEach((videoChunk) -> {
            promptContext.append(videoChunk.toString() + ", ");
        });
        System.out.println("promptContext="+promptContext);

        String llmResponse = llmService.getGeminiResponse(userQuestion, promptContext.toString());
        LLMResponse llmResponseObject = objectMapper.readValue(llmResponse, LLMResponse.class);
        List<Double> llmResponseEmbedding = embeddingService.generateEmbedding(llmResponse);
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setLLMResponse(llmResponse);
        chatHistory.setUserQuestion(userQuestion);
        chatHistory.setUserQuestionEmbedding(userQuestionEmbedding);
        chatHistory.setLLMResponseEmbedding(llmResponseEmbedding);

        chatHistoryRepository.save(chatHistory);

        System.out.println(llmResponseObject);

        return llmResponseObject;
    }

}
