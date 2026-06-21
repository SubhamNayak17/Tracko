package com.logistics.courier_tracking.service;

import com.logistics.courier_tracking.entity.Shipment;
import com.logistics.courier_tracking.enums.ShipmentStatus;
import com.logistics.courier_tracking.exception.BadRequestException;
import com.logistics.courier_tracking.exception.ResourceNotFoundException;
import com.logistics.courier_tracking.repository.ShipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private ShipmentService shipmentService;

    private Shipment shipment;

    @BeforeEach
    void setUp() {
        shipment = new Shipment();
        shipment.setId(1L);
        shipment.setSource("Bangalore");
        shipment.setDestination("Mumbai");
        shipment.setWeight(2.5);
        shipment.setStatus(ShipmentStatus.CREATED);
    }

    // Test 1 — Save shipment — auto generates tracking number
    @Test
    void saveShipment_ShouldAutoGenerateTrackingNumber() {
        // Arrange
        when(shipmentRepository.save(any(Shipment.class)))
                .thenReturn(shipment);

        // Act
        Shipment result = shipmentService.saveShipment(shipment);

        // Assert
        assertNotNull(shipment.getTrackingNumber());
        assertTrue(shipment.getTrackingNumber().startsWith("TRK-"));
        assertEquals(ShipmentStatus.CREATED, shipment.getStatus());
        verify(shipmentRepository, times(1)).save(shipment);
    }

    // Test 2 — Save shipment — same source and destination
    @Test
    void saveShipment_ShouldThrowException_WhenSourceEqualsDestination() {
        // Arrange
        shipment.setDestination("Bangalore");

        // Act and Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> shipmentService.saveShipment(shipment)
        );

        assertEquals("Shipment source and destination cannot be the same",
                exception.getMessage());
        verify(shipmentRepository, never()).save(any());
    }

    // Test 3 — Save shipment — empty source
    @Test
    void saveShipment_ShouldThrowException_WhenSourceIsEmpty() {
        // Arrange
        shipment.setSource("");

        // Act and Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> shipmentService.saveShipment(shipment)
        );

        assertEquals("Shipment source cannot be empty", exception.getMessage());
        verify(shipmentRepository, never()).save(any());
    }

    // Test 4 — Save shipment — invalid weight
    @Test
    void saveShipment_ShouldThrowException_WhenWeightIsZero() {
        // Arrange
        shipment.setWeight(0.0);

        // Act and Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> shipmentService.saveShipment(shipment)
        );

        assertEquals("Shipment weight must be greater than 0",
                exception.getMessage());
        verify(shipmentRepository, never()).save(any());
    }

    // Test 5 — Update status — valid transition CREATED to IN_TRANSIT
    @Test
    void updateShipmentStatus_ShouldUpdate_WhenValidTransition() {
        // Arrange
        when(shipmentRepository.findById(1L))
                .thenReturn(Optional.of(shipment));
        when(shipmentRepository.save(any(Shipment.class)))
                .thenReturn(shipment);

        // Act
        Shipment result = shipmentService.updateShipmentStatus(1L,
                ShipmentStatus.IN_TRANSIT);

        // Assert
        assertEquals(ShipmentStatus.IN_TRANSIT, shipment.getStatus());
        verify(shipmentRepository, times(1)).save(shipment);
    }

    // Test 6 — Update status — invalid transition CREATED to DELIVERED
    @Test
    void updateShipmentStatus_ShouldThrowException_WhenInvalidTransition() {
        // Arrange
        when(shipmentRepository.findById(1L))
                .thenReturn(Optional.of(shipment));

        // Act and Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> shipmentService.updateShipmentStatus(1L,
                        ShipmentStatus.DELIVERED)
        );

        assertTrue(exception.getMessage().contains("Invalid status transition"));
        verify(shipmentRepository, never()).save(any());
    }

    // Test 7 — Update status — shipment not found
    @Test
    void updateShipmentStatus_ShouldThrowException_WhenShipmentNotFound() {
        // Arrange
        when(shipmentRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> shipmentService.updateShipmentStatus(999L,
                        ShipmentStatus.IN_TRANSIT)
        );

        verify(shipmentRepository, never()).save(any());
    }

    // Test 8 — Get shipment by id — not found
    @Test
    void getShipmentById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(shipmentRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act and Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> shipmentService.getShipmentById(999L)
        );

        assertEquals("Shipment not found with id: 999",
                exception.getMessage());
    }
}