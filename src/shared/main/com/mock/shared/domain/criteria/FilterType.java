package com.mock.shared.domain.criteria;

import com.mock.shared.domain.errors.DomainError;
import com.mock.shared.domain.errors.InvalidNullValue;

import java.util.Objects;
import java.util.stream.Stream;

public enum FilterType {
    AND("and"),
    OR("or"),
    NOR("nor"),
    NOT("not");

    private final String type;

    FilterType(String type) {
        this.type = type;
    }

    public static FilterType fromValue(String value) {
        ensureNotNull(value);
        return Stream.of(FilterType.values())
                     .filter(filterType -> filterType.type.equalsIgnoreCase(value))
                     .findFirst()
                     .orElseThrow(() -> new InvalidFilterType(value));
    }

    private static void ensureNotNull(String type) {
        if (Objects.isNull(type)) {
            throw new InvalidNullValue("The filter type is null");
        }
    }

    public boolean isAggregation() {
        return this != AND;
    }

    public String value() {
        return type;
    }

    public static class InvalidFilterType extends DomainError {

        public InvalidFilterType(String type) {
            super("InvalidFilterType",
                  String.format("The type (%s) is invalid",
                                type));
        }
    }

}
