package com.better.PaymentClasses.PaymentInterfaces;

import com.better.PaymentClasses.PaymentResults.PaymentResult;

public interface PaymentMethod {
    PaymentResult process(double amount);
    boolean validate(double amount);
    double calculateFee(double amount);
    String getMethodName();
}
