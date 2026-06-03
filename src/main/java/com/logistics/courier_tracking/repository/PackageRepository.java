package com.logistics.courier_tracking.repository;

import com.logistics.courier_tracking.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {
}
