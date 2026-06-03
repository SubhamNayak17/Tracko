package com.logistics.courier_tracking.service;

import com.logistics.courier_tracking.dto.PageResponse;
import com.logistics.courier_tracking.entity.Payment;
import com.logistics.courier_tracking.enums.PaymentStatus;
import com.logistics.courier_tracking.exception.ResourceNotFoundException;
import com.logistics.courier_tracking.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment savePayment(Payment payment) {
        if (payment.getAmount() <= 0) {
            throw new com.logistics.courier_tracking.exception.BadRequestException("Payment amount must be greater than 0");
        }
        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    public List<Payment> getPaymentsByStatus(PaymentStatus paymentStatus) {
        List<Payment> payments = paymentRepository.findByPaymentStatus(paymentStatus);
        if (payments.isEmpty()) {
            throw new ResourceNotFoundException("No payments found with status: " + paymentStatus);
        }
        return payments;
    }

    public Payment updatePayment(Long id, Payment payment) {
        Payment existing = getPaymentById(id);
        existing.setAmount(payment.getAmount());
        existing.setPaymentMethod(payment.getPaymentMethod());
        existing.setPaymentStatus(payment.getPaymentStatus());
        existing.setPaymentDateTime(payment.getPaymentDateTime());
        existing.setCreatedTime(payment.getCreatedTime());
        existing.setPaymentDate(payment.getPaymentDate());
        return paymentRepository.save(existing);
    }

    public void deletePayment(Long id) {
        Payment existing = getPaymentById(id);
        paymentRepository.delete(existing);
    }

    public List<Payment> saveAllPayments(List<Payment> payments) {
        return paymentRepository.saveAll(payments);
    }
    public PageResponse<Payment> getAllPaymentsPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Payment> paymentPage = paymentRepository.findAll(pageable);
        return new PageResponse<>(
                paymentPage.getContent(),
                paymentPage.getNumber(),
                paymentPage.getSize(),
                paymentPage.getTotalElements(),
                paymentPage.getTotalPages(),
                paymentPage.isLast()
        );
    }

    public PageResponse<Payment> getPaymentsByStatusPaginated(PaymentStatus paymentStatus, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Payment> paymentPage = paymentRepository.findByPaymentStatus(paymentStatus, pageable);
        return new PageResponse<>(
                paymentPage.getContent(),
                paymentPage.getNumber(),
                paymentPage.getSize(),
                paymentPage.getTotalElements(),
                paymentPage.getTotalPages(),
                paymentPage.isLast()
        );
    }
}