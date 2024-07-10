package com.mock.shared.domain.errors;

public class InvalidNullValue extends DomainError {
    public InvalidNullValue(String errorMessage) {
        super("InvalidNullValue",
              errorMessage);
    }

    public InvalidNullValue(String errorCode,
                            String errorMessage) {
        super(errorCode,
              errorMessage);
    }
}
