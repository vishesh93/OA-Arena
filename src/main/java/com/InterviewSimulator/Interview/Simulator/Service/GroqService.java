package com.InterviewSimulator.Interview.Simulator.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroqService {

     @Value("${groq.ai.key}")
      private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public int evaluate(String question, String userAnswer) {

        String prompt = """
            You are a strict technical interviewer.

            Question:
            %s

            User Answer:
            %s
            
                 Rules:
                        - 0 = completely wrong
                        - 5 = partially correct
                        - 10 = perfect answer
                        - Do NOT give explanation
                        - Output ONLY a number
            Give a score from 0 to 10.
            Output ONLY a number.
            """.formatted(question, userAnswer);

        Map<String, Object> request = new HashMap<>();
        request.put("model", "llama-3.1-8b-instant");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of(
                "role", "user",
                "content", prompt
        ));

        request.put("messages", messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.groq.com/openai/v1/chat/completions",
                entity,
                Map.class
        );

        // extract response
        List choices = (List) response.getBody().get("choices");
        Map first = (Map) choices.get(0);
        Map message = (Map) first.get("message");

        String content = (String) message.get("content");

        // clean number
        String cleaned = content.replaceAll("[^0-9]", "");

        if (cleaned.isEmpty()) return 0;

        return Integer.parseInt(cleaned);
    }
}

