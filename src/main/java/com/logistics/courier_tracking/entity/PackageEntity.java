package com.logistics.courier_tracking.entity;

import com.logistics.courier_tracking.enums.PackageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "packages")
public class PackageEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Package type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PackageType packageType;

    @NotNull(message = "Fragile cannot be null")
    private Boolean fragile;

    @NotNull(message = "Length cannot be null")
    @DecimalMin(value = "0.1", message = "Length must be greater than 0")
    private Double length;
    @NotNull(message = "Breadth cannot be null")
    @DecimalMin(value = "0.1", message = "Breadth must be greater than 0")
    private Double breadth;
    @NotNull(message = "Height cannot be null")
    @DecimalMin(value = "0.1", message = "Height must be greater than 0")
    private Double height;

    // Constructors
    public PackageEntity() {}

    public PackageEntity(Long id, PackageType packageType, Boolean fragile, Double length, Double breadth, Double height) {
        this.id = id;
        this.packageType = packageType;
        this.fragile = fragile;
        this.length = length;
        this.breadth = breadth;
        this.height = height;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    public Boolean getFragile() {
        return fragile;
    }

    public void setFragile(Boolean fragile) {
        this.fragile = fragile;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getBreadth() {
        return breadth;
    }

    public void setBreadth(Double breadth) {
        this.breadth = breadth;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
