package com.mock.shared.domain.criteria;

import com.mock.shared.domain.errors.DomainError;
import com.mock.shared.domain.errors.InvalidNullValue;

import java.util.Arrays;
import java.util.Objects;

public enum OrderType {
    ASC("asc"),
    DESC("desc"),
    NONE("none");
    private final String type;

    OrderType(String type) {
        this.type = type;
    }

    public static OrderType fromValue(String value) {
        ensureNotNull(value);
        ensureIsValid(value);
        return Arrays.stream(OrderType.values()).filter(orderType -> orderType.type.equalsIgnoreCase(value))
                     .findFirst()
                     .orElse(NONE);
    }

    private static void ensureIsValid(String type) {
        if (Arrays.stream(OrderType.values())
                  .noneMatch(orderType -> orderType.type.equalsIgnoreCase(type))) {
            throw new InvalidOrderType(type);
        }
    }

    private static void ensureNotNull(String type) {
        if (Objects.isNull(type)) {
            throw new InvalidNullValue("The InvalidOrderType is null");
        }
    }

    public boolean isNone() {
        return this == NONE;
    }

    public boolean isAsc() {
        return this == ASC;
    }

    public String value() {
        return type;
    }

    public static class InvalidOrderType extends DomainError {
        public InvalidOrderType(String orderType) {
            super("InvalidOrderType",
                  String.format("The order type %s is invalid",
                                orderType));
        }
    }
}
