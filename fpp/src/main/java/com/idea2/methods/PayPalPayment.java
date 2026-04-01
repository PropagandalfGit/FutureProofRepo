package com.idea2.methods;

import com.idea2.PaymentMethod;
import com.idea2.PaymentResult;

public class PayPalPayment implements PaymentMethod {

    @Override
    public boolean validate(double amount) {
        return amount > 0 && amount <= 10_000;
    }

    @Override
    public double calculateFee(double amount) {
        return (amount * 0.0349) + 0.49;  // PayPal standard rate
    }

    @Override
    public PaymentResult process(double amount) {
        if (!validate(amount)) {
            return PaymentResult.failure("Amount invalid for PayPal: $" + amount);
        }
        double fee = calculateFee(amount);
        String txId = "PP-" + System.currentTimeMillis();
        System.out.printf("PayPal charged: $%.2f (fee: $%.2f)%n", amount, fee);
        return PaymentResult.success(txId, amount, fee);
    }

    @Override
    public String getMethodName() { return "paypal"; }
}