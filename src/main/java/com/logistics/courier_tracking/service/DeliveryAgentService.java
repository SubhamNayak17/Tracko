package com.logistics.courier_tracking.service;

import com.logistics.courier_tracking.dto.PageResponse;
import com.logistics.courier_tracking.entity.DeliveryAgent;
import com.logistics.courier_tracking.exception.DuplicateResourceException;
import com.logistics.courier_tracking.exception.ResourceNotFoundException;
import com.logistics.courier_tracking.repository.DeliveryAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryAgentService {

    @Autowired
    private DeliveryAgentRepository deliveryAgentRepository;

    public DeliveryAgent saveDeliveryAgent(DeliveryAgent deliveryAgent) {
        if (deliveryAgentRepository.findByContact(deliveryAgent.getContact()).isPresent()) {
            throw new DuplicateResourceException("Delivery agent already exists with contact: " + deliveryAgent.getContact());
        }
        if (deliveryAgentRepository.findByVehicleNumber(deliveryAgent.getVehicleNumber()).isPresent()) {
            throw new DuplicateResourceException("Delivery agent already exists with vehicle number: " + deliveryAgent.getVehicleNumber());
        }
        return deliveryAgentRepository.save(deliveryAgent);
    }

    public List<DeliveryAgent> getAllDeliveryAgents() {
        return deliveryAgentRepository.findAll();
    }

    public DeliveryAgent getDeliveryAgentById(Long id) {
        return deliveryAgentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery agent not found with id: " + id));
    }

    public List<DeliveryAgent> getAvailableAgents() {
        List<DeliveryAgent> agents = deliveryAgentRepository.findByAvailabilityStatus(true);
        if (agents.isEmpty()) {
            throw new ResourceNotFoundException("No available delivery agents found");
        }
        return agents;
    }

    public DeliveryAgent updateDeliveryAgent(Long id, DeliveryAgent deliveryAgent) {
        DeliveryAgent existing = getDeliveryAgentById(id);
        existing.setName(deliveryAgent.getName());
        existing.setContact(deliveryAgent.getContact());
        existing.setVehicleNumber(deliveryAgent.getVehicleNumber());
        existing.setAvailabilityStatus(deliveryAgent.getAvailabilityStatus());
        existing.setRating(deliveryAgent.getRating());
        return deliveryAgentRepository.save(existing);
    }

    public void deleteDeliveryAgent(Long id) {
        DeliveryAgent existing = getDeliveryAgentById(id);
        deliveryAgentRepository.delete(existing);
    }

    public List<DeliveryAgent> saveAllDeliveryAgents(List<DeliveryAgent> deliveryAgents) {
        return deliveryAgentRepository.saveAll(deliveryAgents);
    }
    public PageResponse<DeliveryAgent> getAllDeliveryAgentsPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<DeliveryAgent> agentPage = deliveryAgentRepository.findAll(pageable);
        return new PageResponse<>(
                agentPage.getContent(),
                agentPage.getNumber(),
                agentPage.getSize(),
                agentPage.getTotalElements(),
                agentPage.getTotalPages(),
                agentPage.isLast()
        );
    }
    public PageResponse<DeliveryAgent> getAvailableAgentsPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<DeliveryAgent> agentPage = deliveryAgentRepository.findByAvailabilityStatus(true, pageable);
        return new PageResponse<>(
                agentPage.getContent(),
                agentPage.getNumber(),
                agentPage.getSize(),
                agentPage.getTotalElements(),
                agentPage.getTotalPages(),
                agentPage.isLast()
        );
    }

}