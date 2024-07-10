package com.mock.shared.domain.errors;

public class InvalidValue extends DomainError {
    public InvalidValue(String errorMessage) {
        super("InvalidValue",
              errorMessage);
    }

    public InvalidValue(String errorCode,
                        String errorMessage) {
        super(errorCode,
              errorMessage);
    }
}

