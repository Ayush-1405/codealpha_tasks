package com.example.chatbot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class ChatController {

    private final Map<String, String> faqs = new HashMap<>();

    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        String response = faqs.get(message.toLowerCase());
        if (response != null) {
            return response;
        }
        if (message.equalsIgnoreCase("hello")) {
            return "Hi there!";
        } else if (message.equalsIgnoreCase("how are you?")) {
            return "I'm doing great, thanks for asking!";
        } else {
            return "Sorry, I don't understand.";
        }
    }

    @PostMapping("/api/train")
    public Map<String, Object> train(@RequestParam("file") MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    faqs.put(parts[0].toLowerCase(), parts[1]);
                }
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("faqs", faqs.size());
        return response;
    }

    @GetMapping("/api/questions")
    public Set<String> getQuestions() {
        return faqs.keySet();
    }
}