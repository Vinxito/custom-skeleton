package com.mock.shared.infrastructure.errors;

import com.mock.shared.domain.errors.DomainError;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

public abstract class ErrorContextHandler {

    public abstract HashMap<Class<? extends DomainError>, HttpStatus> handleConflictError();
}
