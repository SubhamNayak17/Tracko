package com.logistics.courier_tracking.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="customers")
public class Customer extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Column(unique=true,nullable = false)
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    private String email;

    @Column(unique = true,nullable = false)
    @NotBlank(message = "Contact cannot be empty")
    @Size(min = 10, max = 10, message = "Contact number must be exactly 10 digits")
    private String contact;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    public Customer() {}

    public Customer(Long id, String name, String email, String contact, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
