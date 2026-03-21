package com.InterviewSimulator.Interview.Simulator.Service;

import com.InterviewSimulator.Interview.Simulator.DTO.Test.TestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.ai.key}")
    private String apiKey ;


    private final String URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    public String generateQuestions() {

        RestTemplate restTemplate = new RestTemplate();

        String prompt = """
                You are a STRICT JSON generator.
                
                Your ONLY task is to generate a JSON response that EXACTLY matches the required schema.
                
                ⚠️ DO NOT:
                
                * Add explanations
                * Add extra fields
                * Add null values
                * Change field names
                * Add introduction sections or instructions sections unless explicitly asked
                * Use different naming conventions
                * Return markdown (no ```json)
                
                ---
                
                ✅ STRICT JSON SCHEMA:
                
                {
                "test_title": "string",
                "sections": [
                {
                "section_title": "string",
                "section_type": "mcq | coding | theory",
                "instructions": "string",
                "questions": [
                {
                // MCQ TYPE
                "question": "string",
                "options": ["string", "string", "string", "string"],
                "correct_answer": "string"
                },
                {
                // CODING TYPE
                "title": "string",
                "description": "string",
                "test_cases": [
                {
                "input": "string",
                "expected_output": "string",
                "isHidden": true or false
                }
                ]
                },
                {
                // THEORY TYPE
                "question": "string"
                }
                ]
                }
                ]
                }
                
                ---
                
                🔥 STRICT RULES (VERY IMPORTANT):
                
                1. Field names MUST match EXACTLY:
                
                   * test_title
                   * section_title
                   * section_type
                   * instructions
                   * questions
                   * question
                   * options
                   * correct_answer
                   * title
                   * description
                   * test_cases
                   * input
                   * expected_output
                   * isHidden
                
                2. NEVER return null values
                   → If data not available, use empty string "" or empty array []
                
                3. section_type MUST be ONLY:
                
                   * "mcq"
                   * "coding"
                   * "theory"
                
                4. CODING questions:
                   * must be from graph and 2d array topic
                   * MUST have title, description, test_cases
                   * MUST NOT include question/options/correct_answer
                
                5. MCQ questions:
                
                   * MUST have question, options, correct_answer
                   * MUST NOT include title/description/test_cases
                
                6. THEORY questions:
                
                   * MUST only have "question"
                   * NO options, NO test_cases
                
                7. test_cases:
                
                   * minimum 2 test cases
                   * isHidden MUST ALWAYS be true or false (never null)
                
                8. instructions:
                
                   * ALWAYS present (can be "" if not needed)
                
                9. DO NOT add any introduction section like:
                   ❌ "Welcome to test"
                   ❌ "Instructions section"
                   ❌ Any extra section
                   
                11. For coding questions, generate input in competitive programming format.
                
                    Rules:
                
                    Always include size variables (n, m, etc.)
                
                    Input must be readable using Scanner.nextInt()
                
                    No brackets, no commas, no labels
                
                    Example:
                
                    4
                    2 7 11 15
                    9
                
                10. Output MUST be VALID JSON
                
                ---
                
                🎯 TASK:
                Generate a test with:
                
                * 1 coding section (2 questions)
                * 1 mcq section (5 questions)
                * 1 theory section (2 questions)
                
                Return ONLY JSON.
                
                """;

        Map<String, Object> textPart = new HashMap<>();
        textPart.put("text", prompt);

        Map<String, Object> part = new HashMap<>();
        part.put("parts", List.of(textPart));

        Map<String, Object> request = new HashMap<>();
        request.put("contents", List.of(part));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(request, headers);

        Map response = restTemplate.postForObject(
                URL + apiKey,
                entity,
                Map.class
        );


        ObjectMapper mapper = new ObjectMapper();



        List candidates = (List) response.get("candidates");
        Map candidate = (Map) candidates.get(0);
        Map content = (Map) candidate.get("content");
        List parts = (List) content.get("parts");
        Map textMap = (Map) parts.get(0);

        String raw = (String) textMap.get("text");

        return raw;


    }
}