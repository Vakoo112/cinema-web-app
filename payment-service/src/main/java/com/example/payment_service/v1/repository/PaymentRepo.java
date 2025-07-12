package com.example.payment_service.v1.repository;

import com.example.payment_service.v1.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
