package com.logistics.courier_tracking.repository;

import com.logistics.courier_tracking.entity.Shipment;
import com.logistics.courier_tracking.enums.ShipmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByTrackingNumber(String trackingNumber);
    List<Shipment> findByDeliveryAgentId(Long deliveryAgentId);
    List<Shipment> findByStatus(ShipmentStatus status);
    List<Shipment> findByCustomerId(Long customerId);
    Page<Shipment> findByCustomerId(Long customerId, Pageable pageable);
    Page<Shipment> findByStatus(ShipmentStatus status, Pageable pageable);
}
