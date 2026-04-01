package com.better.methods;

import com.better.PaymentMethod;
import com.better.PaymentResult;

public class CreditCardPayment implements PaymentMethod {

    @Override
    public boolean validate(double amount) {
        return amount > 0 && amount <= 50_000;  // typical card limit
    }

    @Override
    public double calculateFee(double amount) {
        return (amount * 0.029) + 0.30;  // Stripe-style: 2.9% + $0.30
    }

    @Override
    public PaymentResult process(double amount) {
        if (!validate(amount)) {
            return PaymentResult.failure("Amount invalid for credit card: $" + amount);
        }
        double fee = calculateFee(amount);
        String txId = "CC-" + System.currentTimeMillis();
        System.out.printf("Credit card charged: $%.2f (fee: $%.2f)%n", amount, fee);
        return PaymentResult.success(txId, amount, fee);
    }

    @Override
    public String getMethodName() { return "credit_card"; }
}