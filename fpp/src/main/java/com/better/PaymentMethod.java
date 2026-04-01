package com.better;

public interface PaymentMethod {
    PaymentResult process(double amount);
    boolean validate(double amount);
    double calculateFee(double amount);
    String getMethodName();
}
