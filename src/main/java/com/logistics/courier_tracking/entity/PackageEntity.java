package com.logistics.courier_tracking.entity;

import com.logistics.courier_tracking.enums.PackageType;
import jakarta.persistence.*;

@Entity
@Table(name = "packages")
public class PackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PackageType packageType;

    private Boolean fragile;

    private Double length;
    private Double breadth;
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
