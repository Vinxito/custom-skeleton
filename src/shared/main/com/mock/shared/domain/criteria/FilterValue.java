package com.mock.shared.domain.criteria;

import com.mock.shared.domain.value_objects.ValueObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class FilterValue extends ValueObject<Serializable> {

    public FilterValue() {
        super("");
    }

    public FilterValue(Serializable value) {

        super(value);
    }

    public FilterValue(String value) {
        super(value);
    }

    public FilterValue(List<Serializable> value) {
        super(new ArrayList<Serializable>(value));
    }

    public FilterValue(Integer value) {
        super(value);
    }

    public FilterValue(Boolean value) {
        super(value);
    }

    public static FilterValue empty() {
        return new FilterValue("");
    }
}
