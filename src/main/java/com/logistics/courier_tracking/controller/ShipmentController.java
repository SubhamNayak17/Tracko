package com.logistics.courier_tracking.controller;

import com.logistics.courier_tracking.dto.ApiResponse;
import com.logistics.courier_tracking.dto.PageResponse;
import com.logistics.courier_tracking.entity.Shipment;
import com.logistics.courier_tracking.enums.ShipmentStatus;
import com.logistics.courier_tracking.service.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Shipment>> saveShipment(@Valid @RequestBody Shipment shipment) {
        return ResponseEntity.ok(ApiResponse.created("Shipment created successfully", shipmentService.saveShipment(shipment)));
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<Shipment>>> saveAllShipments(@RequestBody List<Shipment> shipments) {
        return ResponseEntity.ok(ApiResponse.created("Shipments created successfully", shipmentService.saveAllShipments(shipments)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Shipment>>> getAllShipments() {
        return ResponseEntity.ok(ApiResponse.success("Shipments fetched successfully", shipmentService.getAllShipments()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Shipment>> getShipmentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Shipment fetched successfully", shipmentService.getShipmentById(id)));
    }

    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<ApiResponse<Shipment>> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(ApiResponse.success("Shipment fetched successfully", shipmentService.getShipmentByTrackingNumber(trackingNumber)));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<Shipment>>> getShipmentsByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(ApiResponse.success("Shipments fetched successfully", shipmentService.getShipmentsByCustomerId(customerId)));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Shipment>>> getShipmentsByStatus(@PathVariable ShipmentStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Shipments fetched successfully", shipmentService.getShipmentsByStatus(status)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Shipment>> updateShipment(@PathVariable Long id, @Valid @RequestBody Shipment shipment) {
        return ResponseEntity.ok(ApiResponse.success("Shipment updated successfully", shipmentService.updateShipment(id, shipment)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
        return ResponseEntity.ok(ApiResponse.deleted("Shipment deleted successfully"));
    }
    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<PageResponse<Shipment>>> getAllShipmentsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(ApiResponse.success("Shipments fetched successfully",
                shipmentService.getAllShipmentsPaginated(page, size, sortBy, sortDir)));
    }

    @GetMapping("/customer/{customerId}/paginated")
    public ResponseEntity<ApiResponse<PageResponse<Shipment>>> getShipmentsByCustomerIdPaginated(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(ApiResponse.success("Shipments fetched successfully",
                shipmentService.getShipmentsByCustomerIdPaginated(customerId, page, size, sortBy, sortDir)));
    }

    @GetMapping("/status/{status}/paginated")
    public ResponseEntity<ApiResponse<PageResponse<Shipment>>> getShipmentsByStatusPaginated(
            @PathVariable ShipmentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(ApiResponse.success("Shipments fetched successfully",
                shipmentService.getShipmentsByStatusPaginated(status, page, size, sortBy, sortDir)));
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Shipment>> updateShipmentStatus(
            @PathVariable Long id,
            @RequestParam ShipmentStatus status) {
        return ResponseEntity.ok(ApiResponse.success(
                "Shipment status updated successfully",
                shipmentService.updateShipmentStatus(id, status)));
    }
}