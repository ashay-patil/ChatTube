package com.chat.backend.services;

import com.chat.backend.DTO.ChatHistorySearchResult;
import com.chat.backend.DTO.LLMResponse;
import com.chat.backend.DTO.VideoChunkSearchResult;
import com.chat.backend.RAG.ChatHistorySimilaritySearch;
import com.chat.backend.RAG.EmbeddingService;
import com.chat.backend.RAG.LLMService;
import com.chat.backend.RAG.VideoChunkSimilaritySearch;
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
    ChatHistorySimilaritySearch chatHistorySimilaritySearch;

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
        List<ChatHistorySearchResult> chatHistorySimilarityResult = chatHistorySimilaritySearch.search(userQuestionEmbedding);
        System.out.println("videoChunkSimilarityResult="+videoChunkSimilaritySearch);
        System.out.println("chatHistorySimilarityResult="+chatHistorySimilarityResult);

        StringBuilder videoTranscriptPromptContext = new StringBuilder("");

        knowledgeBaseSimilarityResult.forEach((videoChunk) -> {
            videoTranscriptPromptContext.append(videoChunk.toString() + ", ");
        });

        StringBuilder chatHistoryPromptContext = new StringBuilder();

        chatHistorySimilarityResult.forEach((chat) -> {
            chatHistoryPromptContext.append(chat.toString());
        });

        String llmResponse = llmService.getGeminiResponse(userQuestion, videoTranscriptPromptContext.toString(), chatHistoryPromptContext.toString());
        LLMResponse llmResponseObject = objectMapper.readValue(llmResponse, LLMResponse.class);
        List<Double> llmResponseEmbedding = embeddingService.generateEmbedding(llmResponseObject.getResponse());
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setLLMResponse(llmResponseObject.getResponse());
        chatHistory.setUserQuestion(userQuestion);
        chatHistory.setUserQuestionEmbedding(userQuestionEmbedding);
        chatHistory.setLLMResponseEmbedding(llmResponseEmbedding);

        chatHistoryRepository.save(chatHistory);

        System.out.println(llmResponseObject);

        return llmResponseObject;
    }

}
