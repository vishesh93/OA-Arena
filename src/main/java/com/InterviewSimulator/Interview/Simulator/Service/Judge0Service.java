package com.InterviewSimulator.Interview.Simulator.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.hibernate.cfg.JdbcSettings.URL;

@Service
public class Judge0Service {

    private final String URL = "https://ce.judge0.com/submissions?wait=true";

    public String runCode(String code, String input) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> body = new HashMap<>();
            body.put("source_code", code);
            body.put("language_id", 62);
            body.put("stdin", input);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            Map response = restTemplate.postForObject(URL, entity, Map.class);

            // 🔥 DEBUG FULL RESPONSE
            System.out.println("FULL RESPONSE: " + response);

            if (response == null) return "ERROR";

            // 🔥 COMPILE ERROR
            if (response.get("compile_output") != null) {
                System.out.println("COMPILE ERROR: " + response.get("compile_output"));
                return "COMPILE_ERROR: " + response.get("compile_output");
            }

            // 🔥 RUNTIME ERROR
            if (response.get("stderr") != null) {
                System.out.println("RUNTIME ERROR: " + response.get("stderr"));
                return "RUNTIME_ERROR: " + response.get("stderr");
            }

            // 🔥 STATUS CHECK
            Map status = (Map) response.get("status");
            if (status != null && status.get("description") != null) {
                String desc = status.get("description").toString();
                System.out.println("STATUS: " + desc);

                if (desc.toLowerCase().contains("time")) {
                    return "TLE";
                }

                if (!desc.toLowerCase().contains("accepted")) {
                    return "FAILED";
                }
            }

            // 🔥 OUTPUT
            String output = (String) response.get("stdout");

            System.out.println("FINAL OUTPUT: " + output);

            return output != null ? output.trim() : "ERROR";

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 VERY IMPORTANT
            return "ERROR";
        }
    }
}