package com.logistics.courier_tracking.service;

import com.logistics.courier_tracking.dto.PageResponse;
import com.logistics.courier_tracking.entity.PackageEntity;
import com.logistics.courier_tracking.exception.ResourceNotFoundException;
import com.logistics.courier_tracking.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    public PackageEntity savePackage(PackageEntity packageEntity) {
        return packageRepository.save(packageEntity);
    }

    public List<PackageEntity> getAllPackages() {
        return packageRepository.findAll();
    }

    public PackageEntity getPackageById(Long id) {
        return packageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found with id: " + id));
    }

    public PackageEntity updatePackage(Long id, PackageEntity packageEntity) {
        PackageEntity existing = getPackageById(id);
        existing.setPackageType(packageEntity.getPackageType());
        existing.setFragile(packageEntity.getFragile());
        existing.setLength(packageEntity.getLength());
        existing.setBreadth(packageEntity.getBreadth());
        existing.setHeight(packageEntity.getHeight());
        return packageRepository.save(existing);
    }

    public void deletePackage(Long id) {
        PackageEntity existing = getPackageById(id);
        packageRepository.delete(existing);
    }

    public List<PackageEntity> saveAllPackages(List<PackageEntity> packages) {
        return packageRepository.saveAll(packages);
    }
    public PageResponse<PackageEntity> getAllPackagesPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PackageEntity> packagePage = packageRepository.findAll(pageable);
        return new PageResponse<>(
                packagePage.getContent(),
                packagePage.getNumber(),
                packagePage.getSize(),
                packagePage.getTotalElements(),
                packagePage.getTotalPages(),
                packagePage.isLast()
        );
    }
}