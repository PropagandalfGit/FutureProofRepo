package com.better;

import com.better.decorator.LoggingPaymentMethod;
import com.better.methods.CreditCardPayment;
import com.better.methods.CryptoPayment;
import com.better.methods.PayPalPayment;

public class App {
    public static void main(String[] args) {

        PaymentMethodRegistry registry = new PaymentMethodRegistry();

        // Register, wrap each with logging via the decorator
        registry.register(new LoggingPaymentMethod(new CreditCardPayment()));
        registry.register(new LoggingPaymentMethod(new PayPalPayment()));
        registry.register(new LoggingPaymentMethod(new CryptoPayment()));

        PaymentProcessor processor = new PaymentProcessor(registry);

        // Use it, the processor doesn't know or care what's registered
        PaymentResult r1 = processor.processPayment("credit_card", 149.99);
        PaymentResult r2 = processor.processPayment("crypto", 0.50);       // will fail validation
        PaymentResult r3 = processor.processPayment("apple_pay", 49.99);   // not registered

        System.out.println(r1.getMessage());  // Payment successful
        System.out.println(r2.getMessage());  // Minimum crypto payment is $1.00
        System.out.println(r3.getMessage());  // Unknown payment method: apple_pay
    }
}
