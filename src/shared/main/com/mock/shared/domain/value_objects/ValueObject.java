package com.mock.shared.domain.value_objects;

import java.io.Serializable;
import java.util.Objects;


public abstract class ValueObject<T> implements ValueObjectType<T>, Serializable {

    private T value;

    public ValueObject() {
        super();
    }

    public ValueObject(T value) {
        super();
        this.value = value;
    }

    public ValueObject(ValueObjectType<T> valueObjectType) {
        super();
        this.value = valueObjectType.value();
    }

    public final T value() {
        return value;
    }

    public final void value(T value) {
        this.value = value;
    }

    @Override
    public final int hashCode() {
        final int prime  = 31;
        int       result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ValueObject<?> other = (ValueObject<?>) obj;
        if (value == null) {
            return other.value == null;
        } else return value.equals(other.value);
    }

    public boolean isNull() {
        return Objects.isNull(value);
    }

    @Override
    public String toString() {
        if (Objects.nonNull(value)) {
            return value.toString();
        }
        return null;
    }
}

