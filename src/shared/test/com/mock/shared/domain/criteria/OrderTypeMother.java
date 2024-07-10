package com.mock.shared.domain.criteria;

public class OrderTypeMother {

    public static OrderType random() {
        return OrderType.ASC;
    }

    public static String randomValue() {
        return random().value();
    }
}
