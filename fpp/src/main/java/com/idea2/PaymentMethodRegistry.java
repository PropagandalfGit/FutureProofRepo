package com.idea2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PaymentMethodRegistry {
    private final Map<String, PaymentMethod> methods = new HashMap<>();

    public void register(PaymentMethod method) {
        methods.put(method.getMethodName(), method);
    }

    public Optional<PaymentMethod> resolve(String name) {
        return Optional.ofNullable(methods.get(name));
    }
}
