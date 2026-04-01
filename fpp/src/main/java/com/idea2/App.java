package com.idea2;

import com.idea2.decorator.LoggingPaymentMethod;
import com.idea2.methods.CreditCardPayment;
import com.idea2.methods.CryptoPayment;
import com.idea2.methods.PayPalPayment;

public class App {
    public static void main(String[] args) {

        PaymentMethodRegistry registry = new PaymentMethodRegistry();

        // Register, wrap each with logging via the decorator
        registry.register(new LoggingPaymentMethod(new CreditCardPayment()));
        registry.register(new LoggingPaymentMethod(new PayPalPayment()));
        registry.register(new LoggingPaymentMethod(new CryptoPayment()));

        PaymentProcessor processor = new PaymentProcessor(registry);
        
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        // Use it, the processor doesn't know or care what's registered
        System.out.println(ANSI_BLUE + "PROCESSING PAYMENTS" + ANSI_RESET);
        PaymentResult r1 = processor.processPayment("credit_card", 149.99);
        PaymentResult r2 = processor.processPayment("credit_card", -50);
        PaymentResult r3 = processor.processPayment("crypto", 0.50);       // will fail validation
        PaymentResult r4 = processor.processPayment("apple_pay", 49.99);   // not registered
        System.out.println(ANSI_BLUE + "PRINTING MESSAGES" + ANSI_RESET);
        System.out.println(ANSI_GREEN + r1.getMessage() + ANSI_RESET);  // Payment successful
        System.out.println(ANSI_RED + r2.getMessage() + ANSI_RESET);  // Payment unsuccesful, invalid amount
        System.out.println(ANSI_RED + r3.getMessage() + ANSI_RESET);  // Minimum crypto payment is $1.00
        System.out.println(ANSI_YELLOW + r4.getMessage() + ANSI_RESET);  // Unknown payment method: apple_pay
    }
}
