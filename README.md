# Exercise: Future-Proofing a Payment Processor

---

## Overview

You are given a `PaymentProcessor.java` file that works, but is poorly designed, alongside a main `App` class that calls it. Your task is to refactor it into a clean, extensible system without changing what it does, only how it is structured.

By the end of this exercise you will have implemented:

- A more future-proofed Java interface
- Separating responsibilities across focused classes
- Building a runtime registry to eliminate hardcoded branching
- Using the Decorator pattern to add cross-cutting behaviour
- Returning structured result objects instead of void

---

## Getting Started

### Running the starter file

```bash
javac PaymentProcessor.java
java Main
```

You should see payment output printed to the console. This is your baseline, your refactored version should produce equivalent output.

---

## Tasks

Work through these in order. Each task builds on the previous one.

### Task 1 - Create a `PaymentResult` class

The current method returns `void`. The caller has no way to know whether the payment succeeded, what fee was charged, or what the transaction ID was.

Create a `PaymentResult` class that holds:

- A boolean indicating success or failure
- A transaction ID string (We suggest `LocalDateTime.now()` but whatever you use, be consistent)
- The amount charged
- The fee charged
- A human-readable message

Use static factory methods to construct results cleanly:

```java
PaymentResult.success(txId, amount, fee);
PaymentResult.failure("Reason the payment failed");
```

---

### Task 2 - Create a `PaymentMethod` interface

Extract the responsibilities currently buried inside each `if/else` block into a shared contract. Every payment method should be required to implement:

```java
public interface PaymentMethod {
    PaymentResult process(double amount);
    boolean validate(double amount);
    double calculateFee(double amount);
    String getMethodName();
}
```

Think about why each method belongs on the interface; what would break if one were missing?

---

### Task 3 - Implement a class for each payment method

Create one class per payment type, each implementing `PaymentMethod` with the same validation limit and fee amount listed for each each current method in the current implementation.

Each class should call `validate()` at the start of `process()` and return a `PaymentResult.failure(...)` if validation does not pass.

---

### Task 4 - Build a `PaymentMethodRegistry`

Replace the `if/else` chain with a registry that resolves a method by name at runtime.

```java
public class PaymentMethodRegistry {
    public void register(PaymentMethod method) { ... }
    public Optional<PaymentMethod> resolve(String name) { ... }
}
```

#### NOTE: Using `Optional` here is deliberate! It forces the caller to explicitly handle the case rather than doing nothing.

---

### Task 5 - Slim down `PaymentProcessor`

With the registry in place, `PaymentProcessor` should be very thin. Its entire job is to ask the registry for the right method and call it:

```java
public PaymentResult processPayment(String methodName, double amount) {
    return registry.resolve(methodName)
            .map(method -> method.process(amount))
            .orElse(PaymentResult.failure("Unknown payment method: " + methodName));
}
```

---

### Task 6 - Update `Main` to use your results

Update the `main` method to capture and print each `PaymentResult`:

```java
PaymentResult result = processor.processPayment("credit_card", 149.99);
System.out.println(result.getMessage());
System.out.println("Transaction ID: " + result.getTransactionId());
System.out.printf("Fee charged: $%.2f%n", result.getFeeCharged());
```

You will submit screenshots as verification that all four test cases behave correctly - success, failed validation, and trying an unknown method.

---

## Stretch Goals

These are optional but strongly recommended.

**Add a `LoggingPaymentMethod` decorator**

Create a class that wraps any `PaymentMethod` and adds audit logging around it - without modifying the payment classes themselves:

```java
PaymentMethod creditCard = new LoggingPaymentMethod(new CreditCardPayment());
```

The decorator should log the attempt before calling `process()` and log the outcome after. The underlying payment class should contain zero logging code.

**Add a new payment method without touching existing files**

Create a `VenmoPayment` class with a 1.75% flat fee ($0.25 min / $25 max). Register it, and confirm that none of your existing classes needed to change. 

**Write a unit test**

Write a test (JUnit or otherwise) that verifies `CreditCardPayment.calculateFee()` returns the correct value for a known input. Notice how easy this is now that the fee logic lives in its own method on its own class!

---

## Suggested File Structure

```
src/java/com
├── App.java
├── PaymentProcessor.java
├── PaymentMethodRegistry.java
├── PaymentMethod.java
├── PaymentResult.java
├── methods/
│   ├── CreditCardPayment.java
│   ├── PayPalPayment.java
│   └── VenmoPayment.java
└── decorator/
    └── LoggingPaymentMethod.java      <- If you so choose...
```
---