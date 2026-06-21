package com.logistics.courier_tracking.service;

import com.logistics.courier_tracking.entity.Shipment;
import com.logistics.courier_tracking.exception.ResourceNotFoundException;
import com.logistics.courier_tracking.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class AIService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // ─── Idea 1: Natural Language Tracking ───────────────────
    public String trackShipmentWithAI(String userQuery) {

        // Extract tracking number from query
        String trackingNumber = extractTrackingNumber(userQuery);

        // Build shipment context
        String shipmentContext;
        if (trackingNumber != null) {
            shipmentContext = getShipmentContext(trackingNumber);
        } else {
            shipmentContext = "No tracking number found in the query.";
        }

        // Build prompt for Gemini
        String prompt = """
        You are a helpful courier tracking assistant for Tracko.
        
        User Query: %s
        
        Shipment Data from our system:
        %s
        
        Based on the shipment data above, answer the user's query in a friendly,
        concise and helpful manner. If no shipment data is found, politely ask
        the user to provide a valid tracking number.
        
        IMPORTANT: Reply in plain text only. Do not use markdown, 
        asterisks, bold, bullet points or any special characters.
        """.formatted(userQuery, shipmentContext);

        return callGeminiAPI(prompt);
    }

    // ─── Idea 2: Smart Summary Generator ─────────────────────
    public String generateShipmentSummary() {

        List<Shipment> shipments = shipmentRepository.findAll();

        if (shipments.isEmpty()) {
            return "No shipments found in the system.";
        }

        // Build summary data
        long total = shipments.size();
        long created = shipments.stream().filter(s -> s.getStatus().name().equals("CREATED")).count();
        long inTransit = shipments.stream().filter(s -> s.getStatus().name().equals("IN_TRANSIT")).count();
        long outForDelivery = shipments.stream().filter(s -> s.getStatus().name().equals("OUT_FOR_DELIVERY")).count();
        long delivered = shipments.stream().filter(s -> s.getStatus().name().equals("DELIVERED")).count();
        long cancelled = shipments.stream().filter(s -> s.getStatus().name().equals("CANCELLED")).count();

        String dataContext = """
                Total Shipments: %d
                CREATED: %d
                IN_TRANSIT: %d
                OUT_FOR_DELIVERY: %d
                DELIVERED: %d
                CANCELLED: %d
                """.formatted(total, created, inTransit, outForDelivery, delivered, cancelled);

        String prompt = """
        You are a business analyst for Tracko — a courier logistics company.
        
        Here is the current shipment data:
        %s
        
        Generate a professional, concise business summary report based on this data.
        Include insights, percentages, and recommendations.
        Keep it under 150 words.
        
        IMPORTANT: Reply in plain text only. Do not use markdown,
        asterisks, bold, bullet points or any special characters.
        """.formatted(dataContext);

        return callGeminiAPI(prompt);
    }

    // ─── Helper: Extract tracking number from query ───────────
    private String extractTrackingNumber(String query) {
        String[] words = query.split(" ");
        for (String word : words) {
            if (word.startsWith("TRK-")) {
                return word.trim();
            }
        }
        return null;
    }

    // ─── Helper: Get shipment details as text ─────────────────
    private String getShipmentContext(String trackingNumber) {
        return shipmentRepository.findByTrackingNumber(trackingNumber)
                .map(s -> """
                        Tracking Number: %s
                        Status: %s
                        Source: %s
                        Destination: %s
                        Weight: %s kg
                        Shipment Date: %s
                        Expected Delivery: %s
                        Customer: %s
                        Delivery Agent: %s
                        Warehouse: %s
                        Payment Status: %s
                        """.formatted(
                        s.getTrackingNumber(),
                        s.getStatus(),
                        s.getSource(),
                        s.getDestination(),
                        s.getWeight(),
                        s.getShipmentDateTime(),
                        s.getDeliveryDate(),
                        s.getCustomer() != null ? s.getCustomer().getName() : "N/A",
                        s.getDeliveryAgent() != null ? s.getDeliveryAgent().getName() : "N/A",
                        s.getWarehouse() != null ? s.getWarehouse().getName() : "N/A",
                        s.getPayment() != null ? s.getPayment().getPaymentStatus() : "N/A"
                ))
                .orElse("No shipment found with tracking number: " + trackingNumber);
    }

    // ─── Helper: Call Gemini API ──────────────────────────────
    private String callGeminiAPI(String prompt) {
        String url = apiUrl + "?key=" + apiKey;

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        Map<String, Object> part = new HashMap<>();

        part.put("text", prompt);
        content.put("parts", List.of(part));
        requestBody.put("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            Map body = response.getBody();
            List candidates = (List) body.get("candidates");
            Map candidate = (Map) candidates.get(0);
            Map contentMap = (Map) candidate.get("content");
            List parts = (List) contentMap.get("parts");
            Map firstPart = (Map) parts.get(0);
            String rawText = (String) firstPart.get("text");
            return cleanResponse(rawText);
        } catch (Exception e) {
            return "AI service is currently unavailable. Please try again later. Error: " + e.getMessage();
        }
    }
    private String cleanResponse(String text) {
        if (text == null) return "";

        // Replace multiple \n with single space
        text = text.replaceAll("\\n+", " ");

        // Remove any remaining asterisks
        text = text.replaceAll("\\*", "");

        // Remove extra spaces
        text = text.replaceAll(" +", " ");

        // Trim leading and trailing spaces
        text = text.trim();

        return text;
    }
}