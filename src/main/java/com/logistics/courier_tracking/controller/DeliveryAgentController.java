package com.logistics.courier_tracking.controller;

import com.logistics.courier_tracking.dto.ApiResponse;
import com.logistics.courier_tracking.dto.PageResponse;
import com.logistics.courier_tracking.entity.DeliveryAgent;
import com.logistics.courier_tracking.service.DeliveryAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-agents")
public class DeliveryAgentController {

    @Autowired
    private DeliveryAgentService deliveryAgentService;

    @PostMapping
    public ResponseEntity<ApiResponse<DeliveryAgent>> saveDeliveryAgent(@RequestBody DeliveryAgent deliveryAgent) {
        return ResponseEntity.ok(ApiResponse.created("Delivery agent created successfully", deliveryAgentService.saveDeliveryAgent(deliveryAgent)));
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<DeliveryAgent>>> saveAllDeliveryAgents(@RequestBody List<DeliveryAgent> deliveryAgents) {
        return ResponseEntity.ok(ApiResponse.created("Delivery agents created successfully", deliveryAgentService.saveAllDeliveryAgents(deliveryAgents)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DeliveryAgent>>> getAllDeliveryAgents() {
        return ResponseEntity.ok(ApiResponse.success("Delivery agents fetched successfully", deliveryAgentService.getAllDeliveryAgents()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DeliveryAgent>> getDeliveryAgentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Delivery agent fetched successfully", deliveryAgentService.getDeliveryAgentById(id)));
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<DeliveryAgent>>> getAvailableAgents() {
        return ResponseEntity.ok(ApiResponse.success("Available delivery agents fetched successfully", deliveryAgentService.getAvailableAgents()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DeliveryAgent>> updateDeliveryAgent(@PathVariable Long id, @RequestBody DeliveryAgent deliveryAgent) {
        return ResponseEntity.ok(ApiResponse.success("Delivery agent updated successfully", deliveryAgentService.updateDeliveryAgent(id, deliveryAgent)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDeliveryAgent(@PathVariable Long id) {
        deliveryAgentService.deleteDeliveryAgent(id);
        return ResponseEntity.ok(ApiResponse.deleted("Delivery agent deleted successfully"));
    }
    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<PageResponse<DeliveryAgent>>> getAllDeliveryAgentsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(ApiResponse.success("Delivery agents fetched successfully",
                deliveryAgentService.getAllDeliveryAgentsPaginated(page, size, sortBy, sortDir)));
    }

    @GetMapping("/available/paginated")
    public ResponseEntity<ApiResponse<PageResponse<DeliveryAgent>>> getAvailableAgentsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(ApiResponse.success("Available agents fetched successfully",
                deliveryAgentService.getAvailableAgentsPaginated(page, size, sortBy, sortDir)));
    }
}