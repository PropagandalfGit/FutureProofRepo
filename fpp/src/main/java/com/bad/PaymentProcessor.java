package com.bad;

public class PaymentProcessor {
    public void processPayment(String method, double amount) {
        if (method.equals("credit_card")) {
            if (amount <= 0 || amount > 50000) {
                System.out.println("Invalid credit card amount: " + amount);
                return;
            }

            double fee = (amount * 0.029) + 0.30;
            System.out.println("[LOG] Attempting credit card payment of $" + amount);

            System.out.printf("Processing credit card: $%.2f (fee: $%.2f)%n", amount, fee);
            System.out.println("[LOG] Credit card payment successful");

        } else if (method.equals("paypal")) {
            if (amount <= 0 || amount > 10000) {
                System.out.println("Invalid PayPal amount: " + amount);
                return;
            }

            double fee = (amount * 0.0349) + 0.49;

            System.out.println("[LOG] Attempting PayPal payment of $" + amount);

            System.out.printf("Processing PayPal: $%.2f (fee: $%.2f)%n", amount, fee);
            System.out.println("[LOG] PayPal payment successful");

        } else if (method.equals("venmo")) {
            if (amount <= 0 || amount > 5000) {
                System.out.println("Invalid Venmo amount: " + amount);
                return;
            }

            double fee = amount * 0.03;

            System.out.println("[LOG] Attempting Venmo payment of $" + amount);

            System.out.printf("Processing Venmo: $%.2f (fee: $%.2f)%n", amount, fee);
            System.out.println("[LOG] Venmo payment successful");

        } else if (method.equals("crypto")) {
            if (amount < 1.0) {
                System.out.println("Minimum crypto payment is $1.00");
                return;
            }

            double fee = amount * 0.005;

            System.out.println("[LOG] Attempting crypto payment of $" + amount);

            System.out.printf("Processing crypto: $%.2f (fee: $%.2f)%n", amount, fee);
            System.out.println("[LOG] Crypto payment successful");

        } else {
            System.out.println("Unknown payment method: " + method);
        }
    }
}
