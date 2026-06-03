package com.logistics.courier_tracking.service;

import com.logistics.courier_tracking.dto.PageResponse;
import com.logistics.courier_tracking.entity.Warehouse;
import com.logistics.courier_tracking.exception.DuplicateResourceException;
import com.logistics.courier_tracking.exception.ResourceNotFoundException;
import com.logistics.courier_tracking.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public Warehouse saveWarehouse(Warehouse warehouse) {
        if (warehouseRepository.findByContact(warehouse.getContact()).isPresent()) {
            throw new DuplicateResourceException("Warehouse already exists with contact: " + warehouse.getContact());
        }
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + id));
    }

    public List<Warehouse> getWarehousesByLocation(String location) {
        List<Warehouse> warehouses = warehouseRepository.findByLocation(location);
        if (warehouses.isEmpty()) {
            throw new ResourceNotFoundException("No warehouses found in location: " + location);
        }
        return warehouses;
    }

    public Warehouse updateWarehouse(Long id, Warehouse warehouse) {
        Warehouse existing = getWarehouseById(id);
        existing.setName(warehouse.getName());
        existing.setLocation(warehouse.getLocation());
        existing.setCapacity(warehouse.getCapacity());
        existing.setContact(warehouse.getContact());
        existing.setDeliveryDate(warehouse.getDeliveryDate());
        return warehouseRepository.save(existing);
    }

    public void deleteWarehouse(Long id) {
        Warehouse existing = getWarehouseById(id);
        warehouseRepository.delete(existing);
    }

    public List<Warehouse> saveAllWarehouses(List<Warehouse> warehouses) {
        return warehouseRepository.saveAll(warehouses);
    }
    public PageResponse<Warehouse> getAllWarehousesPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Warehouse> warehousePage = warehouseRepository.findAll(pageable);
        return new PageResponse<>(
                warehousePage.getContent(),
                warehousePage.getNumber(),
                warehousePage.getSize(),
                warehousePage.getTotalElements(),
                warehousePage.getTotalPages(),
                warehousePage.isLast()
        );
    }

    public PageResponse<Warehouse> getWarehousesByLocationPaginated(String location, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Warehouse> warehousePage = warehouseRepository.findByLocation(location, pageable);
        return new PageResponse<>(
                warehousePage.getContent(),
                warehousePage.getNumber(),
                warehousePage.getSize(),
                warehousePage.getTotalElements(),
                warehousePage.getTotalPages(),
                warehousePage.isLast()
        );
    }
}