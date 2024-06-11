package com.project.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.payment.domain.CanclePayment;

public interface CanclePaymentRepository extends JpaRepository<CanclePayment, Long> {
}
