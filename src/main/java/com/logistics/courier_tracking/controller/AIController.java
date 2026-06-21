package com.logistics.courier_tracking.controller;

import com.logistics.courier_tracking.dto.ApiResponse;
import com.logistics.courier_tracking.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    // Idea 1 — Natural Language Tracking
    @PostMapping("/track")
    public ResponseEntity<ApiResponse<String>> trackShipment(
            @RequestBody Map<String, String> request) {
        String query = request.get("query");
        if (query == null || query.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.success("Query cannot be empty", null));
        }
        String response = aiService.trackShipmentWithAI(query);
        return ResponseEntity.ok(ApiResponse.success("AI response generated", response));
    }

    // Idea 2 — Smart Summary Generator
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<String>> getShipmentSummary() {
        String summary = aiService.generateShipmentSummary();
        return ResponseEntity.ok(ApiResponse.success("Summary generated successfully", summary));
    }
}