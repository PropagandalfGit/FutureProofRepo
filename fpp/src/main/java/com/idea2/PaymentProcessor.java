package com.idea2;

public class PaymentProcessor {

    private final PaymentMethodRegistry registry;

    public PaymentProcessor(PaymentMethodRegistry registry) {
        this.registry = registry;
    }

    public PaymentResult processPayment(String methodName, double amount) {
        return registry.resolve(methodName)
                .map(method -> method.process(amount))
                .orElse(PaymentResult.failure("Unknown payment method: " + methodName));
    }
}