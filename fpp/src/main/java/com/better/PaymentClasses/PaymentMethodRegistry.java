package com.better.PaymentClasses;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.better.PaymentClasses.PaymentInterfaces.PaymentMethod;

public class PaymentMethodRegistry {
    private final Map<String, PaymentMethod> methods = new HashMap<>();

    public void register(PaymentMethod method) {
        methods.put(method.getMethodName(), method);
    }

    public Optional<PaymentMethod> resolve(String name) {
        return Optional.ofNullable(methods.get(name));
    }
}
