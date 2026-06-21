package com.logistics.courier_tracking.service;

import com.logistics.courier_tracking.entity.DeliveryAgent;
import com.logistics.courier_tracking.exception.DuplicateResourceException;
import com.logistics.courier_tracking.exception.ResourceNotFoundException;
import com.logistics.courier_tracking.repository.DeliveryAgentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryAgentServiceTest {

    @Mock
    private DeliveryAgentRepository deliveryAgentRepository;

    @InjectMocks
    private DeliveryAgentService deliveryAgentService;

    private DeliveryAgent agent;

    @BeforeEach
    void setUp() {
        agent = new DeliveryAgent();
        agent.setId(1L);
        agent.setName("Ravi Kumar");
        agent.setContact("9811122233");
        agent.setVehicleNumber("KA01AB1234");
        agent.setAvailabilityStatus(true);
        agent.setRating(4.5);
    }

    // Test 1 — Save agent — success
    @Test
    void saveDeliveryAgent_ShouldSaveSuccessfully_WhenValidData() {
        // Arrange
        when(deliveryAgentRepository.findByContact(agent.getContact()))
                .thenReturn(Optional.empty());
        when(deliveryAgentRepository.findByVehicleNumber(agent.getVehicleNumber()))
                .thenReturn(Optional.empty());
        when(deliveryAgentRepository.save(agent))
                .thenReturn(agent);

        // Act
        DeliveryAgent result = deliveryAgentService.saveDeliveryAgent(agent);

        // Assert
        assertNotNull(result);
        assertEquals("Ravi Kumar", result.getName());
        verify(deliveryAgentRepository, times(1)).save(agent);
    }

    // Test 2 — Save agent — duplicate contact
    @Test
    void saveDeliveryAgent_ShouldThrowException_WhenContactExists() {
        // Arrange
        when(deliveryAgentRepository.findByContact(agent.getContact()))
                .thenReturn(Optional.of(agent));

        // Act and Assert
        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> deliveryAgentService.saveDeliveryAgent(agent)
        );

        assertEquals("Delivery agent already exists with contact: 9811122233",
                exception.getMessage());
        verify(deliveryAgentRepository, never()).save(any());
    }

    // Test 3 — Save agent — duplicate vehicle number
    @Test
    void saveDeliveryAgent_ShouldThrowException_WhenVehicleNumberExists() {
        // Arrange
        when(deliveryAgentRepository.findByContact(agent.getContact()))
                .thenReturn(Optional.empty());
        when(deliveryAgentRepository.findByVehicleNumber(agent.getVehicleNumber()))
                .thenReturn(Optional.of(agent));

        // Act and Assert
        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> deliveryAgentService.saveDeliveryAgent(agent)
        );

        assertEquals("Delivery agent already exists with vehicle number: KA01AB1234",
                exception.getMessage());
        verify(deliveryAgentRepository, never()).save(any());
    }

    // Test 4 — Get agent by id — success
    @Test
    void getDeliveryAgentById_ShouldReturnAgent_WhenFound() {
        // Arrange
        when(deliveryAgentRepository.findById(1L))
                .thenReturn(Optional.of(agent));

        // Act
        DeliveryAgent result = deliveryAgentService.getDeliveryAgentById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Ravi Kumar", result.getName());
        verify(deliveryAgentRepository, times(1)).findById(1L);
    }

    // Test 5 — Get agent by id — not found
    @Test
    void getDeliveryAgentById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(deliveryAgentRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act and Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> deliveryAgentService.getDeliveryAgentById(999L)
        );

        assertEquals("Delivery agent not found with id: 999",
                exception.getMessage());
    }

    // Test 6 — Get available agents — success
    @Test
    void getAvailableAgents_ShouldReturnAvailableAgents() {
        // Arrange
        when(deliveryAgentRepository.findByAvailabilityStatus(true))
                .thenReturn(List.of(agent));

        // Act
        List<DeliveryAgent> result = deliveryAgentService.getAvailableAgents();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getAvailabilityStatus());
        verify(deliveryAgentRepository, times(1))
                .findByAvailabilityStatus(true);
    }

    // Test 7 — Delete agent — not found
    @Test
    void deleteDeliveryAgent_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(deliveryAgentRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> deliveryAgentService.deleteDeliveryAgent(999L)
        );

        verify(deliveryAgentRepository, never()).delete(any());
    }
}