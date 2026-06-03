package com.logistics.courier_tracking.service;

import com.logistics.courier_tracking.dto.PageResponse;
import com.logistics.courier_tracking.entity.TrackingHistory;
import com.logistics.courier_tracking.exception.ResourceNotFoundException;
import com.logistics.courier_tracking.repository.TrackingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class TrackingHistoryService {

    @Autowired
    private TrackingHistoryRepository trackingHistoryRepository;

    public TrackingHistory saveTrackingHistory(TrackingHistory trackingHistory) {
        return trackingHistoryRepository.save(trackingHistory);
    }

    public List<TrackingHistory> getAllTrackingHistories() {
        return trackingHistoryRepository.findAll();
    }

    public TrackingHistory getTrackingHistoryById(Long id) {
        return trackingHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tracking history not found with id: " + id));
    }

    public List<TrackingHistory> getTrackingHistoryByShipmentId(Long shipmentId) {
        List<TrackingHistory> histories = trackingHistoryRepository.findByShipmentId(shipmentId);
        if (histories.isEmpty()) {
            throw new ResourceNotFoundException("No tracking history found for shipment id: " + shipmentId);
        }
        return histories;
    }

    public TrackingHistory updateTrackingHistory(Long id, TrackingHistory trackingHistory) {
        TrackingHistory existing = getTrackingHistoryById(id);
        existing.setLocation(trackingHistory.getLocation());
        existing.setRemarks(trackingHistory.getRemarks());
        existing.setStatus(trackingHistory.getStatus());
        return trackingHistoryRepository.save(existing);
    }

    public void deleteTrackingHistory(Long id) {
        TrackingHistory existing = getTrackingHistoryById(id);
        trackingHistoryRepository.delete(existing);
    }

    public List<TrackingHistory> saveAllTrackingHistories(List<TrackingHistory> trackingHistories) {
        return trackingHistoryRepository.saveAll(trackingHistories);
    }
    public PageResponse<TrackingHistory> getAllTrackingHistoriesPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TrackingHistory> trackingPage = trackingHistoryRepository.findAll(pageable);
        return new PageResponse<>(
                trackingPage.getContent(),
                trackingPage.getNumber(),
                trackingPage.getSize(),
                trackingPage.getTotalElements(),
                trackingPage.getTotalPages(),
                trackingPage.isLast()
        );
    }

    public PageResponse<TrackingHistory> getTrackingHistoryByShipmentIdPaginated(Long shipmentId, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TrackingHistory> trackingPage = trackingHistoryRepository.findByShipmentId(shipmentId, pageable);
        return new PageResponse<>(
                trackingPage.getContent(),
                trackingPage.getNumber(),
                trackingPage.getSize(),
                trackingPage.getTotalElements(),
                trackingPage.getTotalPages(),
                trackingPage.isLast()
        );
    }
}