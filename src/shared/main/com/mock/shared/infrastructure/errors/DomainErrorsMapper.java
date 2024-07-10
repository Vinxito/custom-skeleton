package com.mock.shared.infrastructure.errors;

import com.mock.shared.domain.Injectable;
import com.mock.shared.domain.errors.DomainError;
import com.mock.shared.infrastructure.google.ReflectionClasspaths;
import org.springframework.http.HttpStatus;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

@Injectable
public final class DomainErrorsMapper {

    private final ReflectionClasspaths                              reflectionClasspaths;
    private       HashMap<Class<? extends DomainError>, HttpStatus> indexedDomainErrors;

    public DomainErrorsMapper(ReflectionClasspaths reflectionClasspaths) {
        this.reflectionClasspaths = reflectionClasspaths;
        Set<Class<? extends ErrorContextHandler>> handlerMappers =
                reflectionClasspaths.bySubType(ErrorContextHandler.class);

        try {
            indexedDomainErrors = formatErrors(handlerMappers);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private HashMap<Class<? extends DomainError>, HttpStatus> formatErrors(Set<Class<? extends ErrorContextHandler>> handlerMappers) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        HashMap<Class<? extends DomainError>, HttpStatus> errors = new HashMap<>();

        for (Class<? extends ErrorContextHandler> handlerMapper : handlerMappers) {
            handlerMapper.getConstructor().newInstance().handleConflictError().forEach((errorClass, httpStatus) -> {
                if (!errors.containsKey(errorClass)) errors.put(errorClass,
                                                                httpStatus);
            });
        }

        return errors;
    }

    public HashMap<Class<? extends DomainError>, HttpStatus> domainErrorsMapped() {
        return indexedDomainErrors;
    }
}
