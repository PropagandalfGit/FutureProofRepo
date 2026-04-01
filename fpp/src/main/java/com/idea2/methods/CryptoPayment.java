package com.idea2.methods;

import com.idea2.PaymentMethod;
import com.idea2.PaymentResult;

public class CryptoPayment implements PaymentMethod {

    @Override
    public boolean validate(double amount) {
        return amount >= 1.0;  // network fees make tiny amounts impractical
    }

    @Override
    public double calculateFee(double amount) {
        return amount * 0.005;  // 0.5% network fee
    }

    @Override
    public PaymentResult process(double amount) {
        if (!validate(amount)) {
            return PaymentResult.failure("Minimum crypto payment is $1.00");
        }
        double fee = calculateFee(amount);
        String txId = "CRYPTO-" + System.currentTimeMillis();
        System.out.printf("Crypto payment submitted: $%.2f (network fee: $%.2f)%n", amount, fee);
        return PaymentResult.success(txId, amount, fee);
    }

    @Override
    public String getMethodName() { return "crypto"; }
}