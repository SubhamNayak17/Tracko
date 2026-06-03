package com.logistics.courier_tracking.controller;

import com.logistics.courier_tracking.dto.ApiResponse;
import com.logistics.courier_tracking.dto.PageResponse;
import com.logistics.courier_tracking.entity.Warehouse;
import com.logistics.courier_tracking.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<ApiResponse<Warehouse>> saveWarehouse(@Valid @RequestBody Warehouse warehouse) {
        return ResponseEntity.ok(ApiResponse.created("Warehouse created successfully", warehouseService.saveWarehouse(warehouse)));
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<Warehouse>>> saveAllWarehouses(@RequestBody List<Warehouse> warehouses) {
        return ResponseEntity.ok(ApiResponse.created("Warehouses created successfully", warehouseService.saveAllWarehouses(warehouses)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Warehouse>>> getAllWarehouses() {
        return ResponseEntity.ok(ApiResponse.success("Warehouses fetched successfully", warehouseService.getAllWarehouses()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Warehouse>> getWarehouseById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Warehouse fetched successfully", warehouseService.getWarehouseById(id)));
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<ApiResponse<List<Warehouse>>> getWarehousesByLocation(@PathVariable String location) {
        return ResponseEntity.ok(ApiResponse.success("Warehouses fetched successfully", warehouseService.getWarehousesByLocation(location)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Warehouse>> updateWarehouse(@PathVariable Long id, @Valid @RequestBody Warehouse warehouse) {
        return ResponseEntity.ok(ApiResponse.success("Warehouse updated successfully", warehouseService.updateWarehouse(id, warehouse)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok(ApiResponse.deleted("Warehouse deleted successfully"));
    }
    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<PageResponse<Warehouse>>> getAllWarehousesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(ApiResponse.success("Warehouses fetched successfully",
                warehouseService.getAllWarehousesPaginated(page, size, sortBy, sortDir)));
    }

    @GetMapping("/location/{location}/paginated")
    public ResponseEntity<ApiResponse<PageResponse<Warehouse>>> getWarehousesByLocationPaginated(
            @PathVariable String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(ApiResponse.success("Warehouses fetched successfully",
                warehouseService.getWarehousesByLocationPaginated(location, page, size, sortBy, sortDir)));
    }
}