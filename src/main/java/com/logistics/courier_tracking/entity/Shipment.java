package com.logistics.courier_tracking.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.logistics.courier_tracking.enums.ShipmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "shipments")
public class Shipment extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tracking number cannot be empty")
    @Column(unique = true, nullable = false)
    private String trackingNumber;

    @NotBlank(message = "Source cannot be empty")
    private String source;

    @NotBlank(message = "Destination cannot be empty")
    private String destination;

    @NotNull(message = "Weight cannot be null")
    @DecimalMin(value = "0.1", message = "Weight must be greater than 0")
    private Double weight;

    private LocalDateTime shipmentDateTime;

    private LocalDate deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "delivery_agent_id")
    private DeliveryAgent deliveryAgent;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne
    @JoinColumn(name = "package_id")
    private PackageEntity packageEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
    private List<TrackingHistory> trackingHistories;

    // Constructors
    public Shipment() {
    }

    public Shipment(Long id, String trackingNumber, String source, String destination, Double weight, LocalDateTime shipmentDateTime, LocalDate deliveryDate, ShipmentStatus status, Customer customer, DeliveryAgent deliveryAgent, Warehouse warehouse, Payment payment, PackageEntity packageEntity, List<TrackingHistory> trackingHistories) {
        this.id = id;
        this.trackingNumber = trackingNumber;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.shipmentDateTime = shipmentDateTime;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.customer = customer;
        this.deliveryAgent = deliveryAgent;
        this.warehouse = warehouse;
        this.payment = payment;
        this.packageEntity = packageEntity;
        this.trackingHistories = trackingHistories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public LocalDateTime getShipmentDateTime() {
        return shipmentDateTime;
    }

    public void setShipmentDateTime(LocalDateTime shipmentDateTime) {
        this.shipmentDateTime = shipmentDateTime;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public DeliveryAgent getDeliveryAgent() {
        return deliveryAgent;
    }

    public void setDeliveryAgent(DeliveryAgent deliveryAgent) {
        this.deliveryAgent = deliveryAgent;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public PackageEntity getPackageEntity() {
        return packageEntity;
    }

    public void setPackageEntity(PackageEntity packageEntity) {
        this.packageEntity = packageEntity;
    }

    public List<TrackingHistory> getTrackingHistories() {
        return trackingHistories;
    }

    public void setTrackingHistories(List<TrackingHistory> trackingHistories) {
        this.trackingHistories = trackingHistories;
    }
}