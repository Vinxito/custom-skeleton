package com.mock.shared.domain.value_objects;

import com.mock.shared.domain.errors.DomainError;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class Identifier implements Serializable {

    protected String value;

    public Identifier(String value) {
        ensureValidUuid(value);

        this.value = value;
    }

    protected Identifier() {
        this.value = null;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Identifier that = (Identifier) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private void ensureValidUuid(String value) throws NotValidIdentifier {
        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException exception) {
            throw new NotValidIdentifier(value);
        }
    }

    @Override
    public String toString() {
        if (value == null) {
            return null;
        }
        return value;

    }

    public static class NotValidIdentifier extends DomainError {
        public NotValidIdentifier(String reportTabId) {
            super("NotValidIdentifier",
                  String.format("Identifier provided: %s nots a valid UUID",
                                reportTabId));
        }
    }
}
