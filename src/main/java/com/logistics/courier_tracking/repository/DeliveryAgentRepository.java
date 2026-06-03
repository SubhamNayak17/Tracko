package com.logistics.courier_tracking.repository;


import com.logistics.courier_tracking.entity.DeliveryAgent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryAgentRepository extends JpaRepository<DeliveryAgent, Long> {
    Optional<DeliveryAgent> findByContact(String contact);
    Optional<DeliveryAgent> findByVehicleNumber(String vehicleNumber);
    List<DeliveryAgent> findByAvailabilityStatus(Boolean availabilityStatus);
    Page<DeliveryAgent> findByAvailabilityStatus(Boolean availabilityStatus, Pageable pageable);
}