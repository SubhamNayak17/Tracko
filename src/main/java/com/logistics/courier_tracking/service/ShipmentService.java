package com.logistics.courier_tracking.service;

import com.logistics.courier_tracking.dto.PageResponse;
import com.logistics.courier_tracking.entity.Shipment;
import com.logistics.courier_tracking.enums.ShipmentStatus;
import com.logistics.courier_tracking.exception.BadRequestException;
import com.logistics.courier_tracking.exception.DuplicateResourceException;
import com.logistics.courier_tracking.exception.ResourceNotFoundException;
import com.logistics.courier_tracking.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    public Shipment saveShipment(Shipment shipment) {
        // Auto generate tracking number
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        shipment.setTrackingNumber("TRK-" + date + "-" + uuid);

        // Existing validations
        if (shipment.getSource() == null || shipment.getSource().isEmpty()) {
            throw new BadRequestException("Shipment source cannot be empty");
        }
        if (shipment.getDestination() == null || shipment.getDestination().isEmpty()) {
            throw new BadRequestException("Shipment destination cannot be empty");
        }
        if (shipment.getSource().equalsIgnoreCase(shipment.getDestination())) {
            throw new BadRequestException("Shipment source and destination cannot be the same");
        }
        if (shipment.getWeight() == null || shipment.getWeight() <= 0) {
            throw new BadRequestException("Shipment weight must be greater than 0");
        }
        shipment.setStatus(ShipmentStatus.CREATED);
        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment getShipmentById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id));
    }

    public Shipment getShipmentByTrackingNumber(String trackingNumber) {
        return shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with tracking number: " + trackingNumber));
    }


    public List<Shipment> getShipmentsByStatus(ShipmentStatus status) {
        List<Shipment> shipments = shipmentRepository.findByStatus(status);
        if (shipments.isEmpty()) {
            throw new ResourceNotFoundException("No shipments found with status: " + status);
        }
        return shipments;
    }

    public Shipment updateShipment(Long id, Shipment shipment) {
        Shipment existing = getShipmentById(id);
        // Notice - trackingNumber is NOT updated
        existing.setSource(shipment.getSource());
        existing.setDestination(shipment.getDestination());
        existing.setWeight(shipment.getWeight());
        existing.setShipmentDateTime(shipment.getShipmentDateTime());
        existing.setDeliveryDate(shipment.getDeliveryDate());
        existing.setStatus(shipment.getStatus());
        existing.setCustomer(shipment.getCustomer());
        existing.setDeliveryAgent(shipment.getDeliveryAgent());
        existing.setWarehouse(shipment.getWarehouse());
        existing.setPayment(shipment.getPayment());
        existing.setPackageEntity(shipment.getPackageEntity());
        return shipmentRepository.save(existing);
    }

    public void deleteShipment(Long id) {
        Shipment existing = getShipmentById(id);
        shipmentRepository.delete(existing);
    }
    public PageResponse<Shipment> getAllShipmentsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Shipment> shipmentPage = shipmentRepository.findAll(pageable);
        return new PageResponse<>(
                shipmentPage.getContent(),
                shipmentPage.getNumber(),
                shipmentPage.getSize(),
                shipmentPage.getTotalElements(),
                shipmentPage.getTotalPages(),
                shipmentPage.isLast()
        );
    }



    public List<Shipment> saveAllShipments(List<Shipment> shipments) {
        shipments.forEach(shipment -> {
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String uuid = UUID.randomUUID().toString().substring(0, 8);
            shipment.setTrackingNumber("TRK-" + date + "-" + uuid);
            shipment.setStatus(ShipmentStatus.CREATED);
        });
        return shipmentRepository.saveAll(shipments);
    }

    public List<Shipment> getShipmentsByCustomerId(Long customerId) {
        List<Shipment> shipments = shipmentRepository.findByCustomerId(customerId);
        if (shipments.isEmpty()) {
            throw new ResourceNotFoundException("No shipments found for customer id: " + customerId);
        }
        return shipments;
    }
    public PageResponse<Shipment> getAllShipmentsPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Shipment> shipmentPage = shipmentRepository.findAll(pageable);
        return new PageResponse<>(
                shipmentPage.getContent(),
                shipmentPage.getNumber(),
                shipmentPage.getSize(),
                shipmentPage.getTotalElements(),
                shipmentPage.getTotalPages(),
                shipmentPage.isLast()
        );
    }

    public PageResponse<Shipment> getShipmentsByCustomerIdPaginated(Long customerId, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Shipment> shipmentPage = shipmentRepository.findByCustomerId(customerId, pageable);
        return new PageResponse<>(
                shipmentPage.getContent(),
                shipmentPage.getNumber(),
                shipmentPage.getSize(),
                shipmentPage.getTotalElements(),
                shipmentPage.getTotalPages(),
                shipmentPage.isLast()
        );
    }

    public PageResponse<Shipment> getShipmentsByStatusPaginated(ShipmentStatus status, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Shipment> shipmentPage = shipmentRepository.findByStatus(status, pageable);
        return new PageResponse<>(
                shipmentPage.getContent(),
                shipmentPage.getNumber(),
                shipmentPage.getSize(),
                shipmentPage.getTotalElements(),
                shipmentPage.getTotalPages(),
                shipmentPage.isLast()
        );
    }
}