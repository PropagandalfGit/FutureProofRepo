package com.better;

public class PaymentResult {
    private final boolean success;
    private final String transactionId;
    private final double amountCharged;
    private final double feeCharged;
    private final String message;

    // Private constructor — use factory methods
    private PaymentResult(boolean success, String transactionId,
                          double amount, double fee, String message) {
        this.success = success;
        this.transactionId = transactionId;
        this.amountCharged = amount;
        this.feeCharged = fee;
        this.message = message;
    }

    public static PaymentResult success(String txId, double amount, double fee) {
        return new PaymentResult(true, txId, amount, fee, "Payment successful");
    }

    public static PaymentResult failure(String reason) {
        return new PaymentResult(false, null, 0, 0, reason);
    }

    public boolean isSuccess()        { return success; }
    public String getTransactionId()  { return transactionId; }
    public double getAmountCharged()  { return amountCharged; }
    public double getFeeCharged()     { return feeCharged; }
    public String getMessage()        { return message; }
}
