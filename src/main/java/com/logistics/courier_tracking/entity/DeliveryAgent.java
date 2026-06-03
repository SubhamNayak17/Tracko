package com.logistics.courier_tracking.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "delivery_agents")
public class DeliveryAgent extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true, nullable = false, length = 10)
    @Size(min = 10, max = 10, message = "Contact number must be exactly 10 digits")
    private String contact;

    @Column(unique = true, nullable = false)
    private String vehicleNumber;

    private Boolean availabilityStatus;
    private Double rating;

    public DeliveryAgent() {
    }

    public DeliveryAgent(Long id, String name, String contact, String vehicleNumber, Boolean availabilityStatus, Double rating) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.vehicleNumber = vehicleNumber;
        this.availabilityStatus = availabilityStatus;
        this.rating = rating;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Boolean getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(Boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
