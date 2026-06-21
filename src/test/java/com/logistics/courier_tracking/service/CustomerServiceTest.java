package com.logistics.courier_tracking.service;

import com.logistics.courier_tracking.entity.Customer;
import com.logistics.courier_tracking.exception.DuplicateResourceException;
import com.logistics.courier_tracking.exception.ResourceNotFoundException;
import com.logistics.courier_tracking.repository.CustomerRepository;
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
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Rahul Sharma");
        customer.setEmail("rahul@gmail.com");
        customer.setContact("9876543210");
        customer.setAddress("Bangalore");
    }

    // Test 1 — Get customer by id — success
    @Test
    void getCustomerById_ShouldReturnCustomer_WhenFound() {
        // Arrange
        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        // Act
        Customer result = customerService.getCustomerById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Rahul Sharma", result.getName());
        assertEquals("rahul@gmail.com", result.getEmail());
        verify(customerRepository, times(1)).findById(1L);
    }

    // Test 2 — Get customer by id — not found
    @Test
    void getCustomerById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(customerRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act and Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.getCustomerById(999L)
        );

        assertEquals("Customer not found with id: 999", exception.getMessage());
        verify(customerRepository, times(1)).findById(999L);
    }

    // Test 3 — Save customer — success
    @Test
    void saveCustomer_ShouldSaveSuccessfully_WhenValidData() {
        // Arrange
        when(customerRepository.findByEmail(customer.getEmail()))
                .thenReturn(Optional.empty());
        when(customerRepository.findByContact(customer.getContact()))
                .thenReturn(Optional.empty());
        when(customerRepository.save(customer))
                .thenReturn(customer);

        // Act
        Customer result = customerService.saveCustomer(customer);

        // Assert
        assertNotNull(result);
        assertEquals("Rahul Sharma", result.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    // Test 4 — Save customer — duplicate email
    @Test
    void saveCustomer_ShouldThrowException_WhenEmailExists() {
        // Arrange
        when(customerRepository.findByEmail(customer.getEmail()))
                .thenReturn(Optional.of(customer));

        // Act and Assert
        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> customerService.saveCustomer(customer)
        );

        assertEquals("Customer already exists with email: rahul@gmail.com",
                exception.getMessage());
        verify(customerRepository, never()).save(any());
    }

    // Test 5 — Save customer — duplicate contact
    @Test
    void saveCustomer_ShouldThrowException_WhenContactExists() {
        // Arrange
        when(customerRepository.findByEmail(customer.getEmail()))
                .thenReturn(Optional.empty());
        when(customerRepository.findByContact(customer.getContact()))
                .thenReturn(Optional.of(customer));

        // Act and Assert
        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> customerService.saveCustomer(customer)
        );

        assertEquals("Customer already exists with contact: 9876543210",
                exception.getMessage());
        verify(customerRepository, never()).save(any());
    }

    // Test 6 — Delete customer — not found
    @Test
    void deleteCustomer_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(customerRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.deleteCustomer(999L)
        );

        verify(customerRepository, never()).delete(any());
    }
}