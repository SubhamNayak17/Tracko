package com.logistics.courier_tracking.repository;

import com.logistics.courier_tracking.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findByContact(String contact);
    List<Warehouse> findByLocation(String location);
    Page<Warehouse> findByLocation(String location, Pageable pageable);
}
