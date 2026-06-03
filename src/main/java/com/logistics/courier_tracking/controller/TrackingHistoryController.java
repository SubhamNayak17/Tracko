package com.logistics.courier_tracking.controller;

import com.logistics.courier_tracking.dto.ApiResponse;
import com.logistics.courier_tracking.dto.PageResponse;
import com.logistics.courier_tracking.entity.TrackingHistory;
import com.logistics.courier_tracking.service.TrackingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracking-history")
public class TrackingHistoryController {

    @Autowired
    private TrackingHistoryService trackingHistoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<TrackingHistory>> saveTrackingHistory(@RequestBody TrackingHistory trackingHistory) {
        return ResponseEntity.ok(ApiResponse.created("Tracking history created successfully", trackingHistoryService.saveTrackingHistory(trackingHistory)));
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<TrackingHistory>>> saveAllTrackingHistories(@RequestBody List<TrackingHistory> trackingHistories) {
        return ResponseEntity.ok(ApiResponse.created("Tracking histories created successfully", trackingHistoryService.saveAllTrackingHistories(trackingHistories)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TrackingHistory>>> getAllTrackingHistories() {
        return ResponseEntity.ok(ApiResponse.success("Tracking histories fetched successfully", trackingHistoryService.getAllTrackingHistories()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TrackingHistory>> getTrackingHistoryById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Tracking history fetched successfully", trackingHistoryService.getTrackingHistoryById(id)));
    }

    @GetMapping("/shipment/{shipmentId}")
    public ResponseEntity<ApiResponse<List<TrackingHistory>>> getTrackingHistoryByShipmentId(@PathVariable Long shipmentId) {
        return ResponseEntity.ok(ApiResponse.success("Tracking history fetched successfully", trackingHistoryService.getTrackingHistoryByShipmentId(shipmentId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TrackingHistory>> updateTrackingHistory(@PathVariable Long id, @RequestBody TrackingHistory trackingHistory) {
        return ResponseEntity.ok(ApiResponse.success("Tracking history updated successfully", trackingHistoryService.updateTrackingHistory(id, trackingHistory)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTrackingHistory(@PathVariable Long id) {
        trackingHistoryService.deleteTrackingHistory(id);
        return ResponseEntity.ok(ApiResponse.deleted("Tracking history deleted successfully"));
    }
    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<PageResponse<TrackingHistory>>> getAllTrackingHistoriesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(ApiResponse.success("Tracking histories fetched successfully",
                trackingHistoryService.getAllTrackingHistoriesPaginated(page, size, sortBy, sortDir)));
    }

    @GetMapping("/shipment/{shipmentId}/paginated")
    public ResponseEntity<ApiResponse<PageResponse<TrackingHistory>>> getTrackingHistoryByShipmentIdPaginated(
            @PathVariable Long shipmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(ApiResponse.success("Tracking history fetched successfully",
                trackingHistoryService.getTrackingHistoryByShipmentIdPaginated(shipmentId, page, size, sortBy, sortDir)));
    }
}