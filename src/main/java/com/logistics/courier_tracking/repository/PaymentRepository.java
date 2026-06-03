package com.logistics.courier_tracking.repository;

import com.logistics.courier_tracking.entity.Payment;
import com.logistics.courier_tracking.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);
    Page<Payment> findByPaymentStatus(PaymentStatus paymentStatus, Pageable pageable);
}
