package com.mock.shared.domain.criteria;

import com.mock.shared.domain.errors.InvalidNullValue;
import com.mock.shared.domain.errors.InvalidValue;
import com.mock.shared.domain.value_objects.ValueObject;

import java.util.Objects;

public final class OrderBy extends ValueObject<String> {
    public OrderBy() {
        super();
    }

    public OrderBy(String value) {
        super(value);
        ensureNotNull(value);
        ensureNotEmpty(value);
    }

    public static OrderBy create(String orderBy) {
        return new OrderBy(orderBy);
    }

    private static void ensureNotNull(String value) {
        if (Objects.isNull(value)) {
            throw new InvalidNullValue("The order by is null");
        }
    }

    private static void ensureNotEmpty(String value) {
        if (value.isEmpty()) {
            throw new InvalidValue("The order by is empty");
        }
    }

    public static OrderBy empty() {
        return new OrderBy();
    }
}
