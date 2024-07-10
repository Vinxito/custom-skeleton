package com.mock.shared.domain.criteria;

import com.mock.shared.domain.errors.DomainError;
import com.mock.shared.domain.errors.InvalidNullValue;

import java.util.Arrays;
import java.util.Objects;

public enum FilterOperator {
    EQUAL("="),
    EQUAL_IGNORE_CASE("=I"),
    NOT_EQUAL("!="),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
    CONTAINS("CONTAINS"),
    NOT_CONTAINS("NOT_CONTAINS"),
    STARTS_WITH("STARTS_WITH"),
    IN("in"),
    EXISTS("EXISTS"),
    ELEMENT_MATCH("ELEMENT_MATCH");

    private final String operator;

    FilterOperator(String operator) {

        this.operator = operator.toUpperCase();
    }

    public static FilterOperator fromValue(String value) {
        ensureNotNull(value);
        //ensureIsValid(value);

        switch (value.toUpperCase()) {
            case "=":
                return FilterOperator.EQUAL;
            case "=I":
                return FilterOperator.EQUAL_IGNORE_CASE;
            case "!=":
                return FilterOperator.NOT_EQUAL;
            case ">":
                return FilterOperator.GT;
            case ">=":
                return FilterOperator.GTE;
            case "<":
                return FilterOperator.LT;
            case "<=":
                return FilterOperator.LTE;
            case "CONTAINS":
                return FilterOperator.CONTAINS;
            case "NOT_CONTAINS":
                return FilterOperator.NOT_CONTAINS;
            case "STARTS_WITH":
                return FilterOperator.STARTS_WITH;
            case "IN":
                return FilterOperator.IN;
            case "EXISTS":
                return FilterOperator.EXISTS;
            case "ELEMENT_MATCH":
                return FilterOperator.ELEMENT_MATCH;
            default:
                return null;
        }
    }

    private static void ensureIsValid(String operator) {
        if (Arrays.stream(FilterOperator.values())
                  .noneMatch(filterOperator -> filterOperator.operator.equalsIgnoreCase(operator))) {
            throw new InvalidFilterOperation(operator);
        }
    }

    private static void ensureNotNull(String operator) {
        if (Objects.isNull(operator)) {
            throw new InvalidNullValue("The FilterOperation is null");
        }
    }

    public boolean isPositive() {
        return this != NOT_EQUAL && this != NOT_CONTAINS;
    }

    public String value() {
        return operator;
    }

    public static class InvalidFilterOperation extends DomainError {
        public InvalidFilterOperation(String value) {
            super("InvalidFilterOperation",
                  String.format("The filter operation %s is invalid",
                                value));
        }
    }
}
