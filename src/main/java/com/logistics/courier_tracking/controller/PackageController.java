package com.logistics.courier_tracking.controller;

import com.logistics.courier_tracking.dto.ApiResponse;
import com.logistics.courier_tracking.dto.PageResponse;
import com.logistics.courier_tracking.entity.PackageEntity;
import com.logistics.courier_tracking.service.PackageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @PostMapping
    public ResponseEntity<ApiResponse<PackageEntity>> savePackage(@Valid @RequestBody PackageEntity packageEntity) {
        return ResponseEntity.ok(ApiResponse.created("Package created successfully", packageService.savePackage(packageEntity)));
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<PackageEntity>>> saveAllPackages(@RequestBody List<PackageEntity> packages) {
        return ResponseEntity.ok(ApiResponse.created("Packages created successfully", packageService.saveAllPackages(packages)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PackageEntity>>> getAllPackages() {
        return ResponseEntity.ok(ApiResponse.success("Packages fetched successfully", packageService.getAllPackages()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PackageEntity>> getPackageById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Package fetched successfully", packageService.getPackageById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PackageEntity>> updatePackage(@PathVariable Long id, @Valid @RequestBody PackageEntity packageEntity) {
        return ResponseEntity.ok(ApiResponse.success("Package updated successfully", packageService.updatePackage(id, packageEntity)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePackage(@PathVariable Long id) {
        packageService.deletePackage(id);
        return ResponseEntity.ok(ApiResponse.deleted("Package deleted successfully"));
    }
    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<PageResponse<PackageEntity>>> getAllPackagesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(ApiResponse.success("Packages fetched successfully",
                packageService.getAllPackagesPaginated(page, size, sortBy, sortDir)));
    }
}