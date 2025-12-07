package com.pizzastore.paymentservice.service;

import com.pizzastore.paymentservice.beans.Payment;

public interface PaymentService {
    
    Payment processPayment(Payment payment);
    
    Payment getPaymentStatus(Long orderId);
}