package com.mock.shared.infrastructure.spring;

import com.mock.shared.domain.Injectable;
import com.mock.shared.domain.Logger;
import com.mock.shared.domain.errors.DomainError;
import com.mock.shared.infrastructure.errors.DomainErrorsMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.NestedServletException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Injectable
public final class ApiExceptionMiddleware implements Filter {

    private final DomainErrorsMapper errorsMapper;
    private final Logger             log;

    public ApiExceptionMiddleware(DomainErrorsMapper errorsMapper,
                                  Logger log) {
        this.errorsMapper = errorsMapper;
        this.log          = log;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
                        ) throws ServletException {
        HttpServletResponse httpResponse = ((HttpServletResponse) response);

        try {
            try {
                chain.doFilter(request,
                               response);
            } catch (NestedServletException exception) {
                handleCustomError(response,
                                  httpResponse,
                                  exception);
            }
        } catch (Exception exception) {
            log.error(exception.getMessage(),
                      exception);
            throw new ServletException(exception);
        }
    }

    @Override
    public void destroy() {

    }

    private void handleCustomError(ServletResponse response,
                                   HttpServletResponse httpResponse,
                                   NestedServletException exception) throws Exception {

        Throwable error = Arrays.stream(ExceptionUtils.getThrowables(exception))
                                .filter(Objects::nonNull)
                                .filter(element -> element instanceof DomainError)
                                .findFirst()
                                .orElseGet(exception::getCause);

        try {
            String errorMessage = error.getMessage();
            log.error(errorMessage,
                      exception);
            HashMap<Class<? extends DomainError>, HttpStatus> errorMapping = errorsMapper.domainErrorsMapped();
            String                                            errorCode    = errorCodeFor(error);
            int statusCode = statusFor(errorMapping,
                                       error);
            createErrorResponseHttp(response,
                                    httpResponse,
                                    errorCode,
                                    errorMessage,
                                    statusCode);
        } catch (Exception exc) {
            log.error(exc.getMessage(),
                      exc);
            throw new Exception(error);
        }
    }

    private void createErrorResponseHttp(ServletResponse response,
                                         HttpServletResponse httpResponse,
                                         String errorCode,
                                         String errorMessage,
                                         int statusCode) throws IOException {
        httpResponse.setHeader("Content-Type",
                               "application/json");
        httpResponse.setStatus(statusCode);
        PrintWriter writer = response.getWriter();
        writer.write(String.format(
                "{\"error_code\": \"%s\", \"message\": \"%s\"}",
                errorCode,
                errorMessage
                                  ));
        writer.close();
    }

    private String errorCodeFor(Throwable error) {
        if (error instanceof DomainError) {
            return ((DomainError) error).errorCode();
        }
        return error.getClass().toString();
    }

    private int statusFor(HashMap<Class<? extends DomainError>, HttpStatus> errorMapping,
                          Throwable error) {
        HttpStatus status = errorMapping.get(error.getClass());
        if (Objects.nonNull(status)) {
            return status.value();
        }

        Optional<Class<? extends DomainError>> clazzOptional = errorMapping.keySet().stream()
                                                                           .filter(clazz -> clazz.isAssignableFrom(error.getClass()))
                                                                           .findFirst();
        return clazzOptional
                .map(aClass -> errorMapping.get(aClass).value())
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
