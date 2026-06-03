package com.logistics.courier_tracking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.logistics.courier_tracking.enums.ShipmentStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "tracking_history")
public class TrackingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    private String location;

    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    // Constructors
    public TrackingHistory() {}

    public TrackingHistory(Long id, String location, String remarks, ShipmentStatus status) {
        this.id = id;
        this.location = location;
        this.remarks = remarks;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shipment getShipment() { return shipment; }
    public void setShipment(Shipment shipment) { this.shipment = shipment; }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }
}
