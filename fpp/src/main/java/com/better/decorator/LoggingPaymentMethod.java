package com.better.decorator;

import com.better.PaymentMethod;
import com.better.PaymentResult;

public class LoggingPaymentMethod implements PaymentMethod {

    private final PaymentMethod delegate;

    public LoggingPaymentMethod(PaymentMethod delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean validate(double amount) {
        return delegate.validate(amount);
    }

    @Override
    public double calculateFee(double amount) {
        return delegate.calculateFee(amount);
    }

    @Override
    public PaymentResult process(double amount) {
        System.out.printf("[AUDIT] Attempting %s payment of $%.2f%n",
                delegate.getMethodName(), amount);

        PaymentResult result = delegate.process(amount);

        if (result.isSuccess()) {
            System.out.printf("[AUDIT] Success — txId: %s, fee: $%.2f%n",
                    result.getTransactionId(), result.getFeeCharged());
        } else {
            System.out.printf("[AUDIT] Failed — reason: %s%n", result.getMessage());
        }
        return result;
    }

    @Override
    public String getMethodName() {
        return delegate.getMethodName();
    }
}
