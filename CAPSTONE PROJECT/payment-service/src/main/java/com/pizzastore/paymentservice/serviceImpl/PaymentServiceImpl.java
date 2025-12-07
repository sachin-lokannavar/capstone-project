package com.pizzastore.paymentservice.serviceImpl;

import com.pizzastore.paymentservice.beans.Payment;
import com.pizzastore.paymentservice.repository.PaymentRepository;
import com.pizzastore.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment processPayment(Payment payment) {
        
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setTransactionTime(LocalDateTime.now());                
        payment.setStatus("SUCCESS");
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentStatus(Long orderId) {       
        return paymentRepository.findByOrderId(orderId);
    }
}