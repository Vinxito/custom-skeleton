package com.mock.shared.domain.criteria;

import com.mock.shared.domain.errors.InvalidEmptyValue;
import com.mock.shared.domain.errors.InvalidNullValue;
import com.mock.shared.domain.value_objects.ValueObject;

import java.util.Objects;

public final class FilterField extends ValueObject<String> {

    public FilterField(String value) {
        super(value);
        ensureIsValid(value);
    }

    public FilterField() {
        super();
    }

    public static FilterField empty() {
        return new FilterField();
    }

    private void ensureIsValid(String value) {
        if (Objects.isNull(value)) {
            throw new InvalidNullValue("The filterField is null");
        }
        if (value.isEmpty()) {
            throw new InvalidEmptyValue("The filterField is empty");
        }
    }
}
