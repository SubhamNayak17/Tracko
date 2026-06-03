package com.logistics.courier_tracking.repository;

import com.logistics.courier_tracking.entity.TrackingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackingHistoryRepository extends JpaRepository<TrackingHistory, Long> {
    List<TrackingHistory> findByShipmentId(Long shipmentId);
    Page<TrackingHistory> findByShipmentId(Long shipmentId, Pageable pageable);
}
