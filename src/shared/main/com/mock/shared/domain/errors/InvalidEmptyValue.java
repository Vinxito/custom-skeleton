package com.mock.shared.domain.errors;

public class InvalidEmptyValue extends DomainError {
    public InvalidEmptyValue(String errorMessage) {
        super("InvalidEmptyValue",
              errorMessage);
    }

    public InvalidEmptyValue(String errorCode,
                             String errorMessage) {
        super(errorCode,
              errorMessage);
    }
}
