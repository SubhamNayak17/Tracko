package com.logistics.courier_tracking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name="warehouses")
public class Warehouse extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Location cannot be empty")
    private String location;

    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
    @NotBlank(message = "Contact cannot be empty")
    @Column(length = 10, unique = true, nullable = false)
    @Size(min = 10, max = 10, message = "Contact number must be exactly 10 digits")
    private String contact;

    private LocalDate deliveryDate;

    public Warehouse() {
    }

    public Warehouse(Long id, String name, String location, Integer capacity, String contact, LocalDate deliveryDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.contact = contact;
        this.deliveryDate = deliveryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}