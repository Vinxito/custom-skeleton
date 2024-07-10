package com.mock.shared.infrastructure.bus.query;

import com.mock.shared.domain.Injectable;
import com.mock.shared.domain.bus.query.Query;
import com.mock.shared.domain.bus.query.UseCaseQueryHandler;
import com.mock.shared.infrastructure.google.ReflectionClasspaths;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Injectable
public final class QueryHandlersMap {
    private final HashMap<Class<? extends Query>, Class<? extends UseCaseQueryHandler>> indexedQueryHandlers;
    private final ReflectionClasspaths                                                  classPaths;

    public QueryHandlersMap(ReflectionClasspaths classPaths) {
        this.classPaths = classPaths;
        Set<Class<? extends UseCaseQueryHandler>> classes = classPaths.bySubType(UseCaseQueryHandler.class);

        indexedQueryHandlers = formatHandlers(classes);
        validateExitsHandlersForQueries(classPaths.bySubType(Query.class));
    }

    private void validateExitsHandlersForQueries(Set<Class<? extends Query>> queries) {
        Set<Class<?>> subtypes = new HashSet<>();
        for (Class<? extends Query> query : queries) {
            subtypes.clear();
            subtypes.addAll(getInheritClasses(query));
        }
    }

    private Set<Class<?>> getInheritClasses(Class<?> clazz) {
        Set<Class<?>> classes        = new HashSet<>();
        Set<Class<?>> inheritClasses = classPaths.bySubType(clazz);
        inheritClasses.forEach(inheritClazz -> {
            classes.addAll(getInheritClasses(inheritClazz));
        });

        return classes;
    }

    public Class<? extends UseCaseQueryHandler> search(Class<? extends Query> queryClass) {
        return indexedQueryHandlers.get(queryClass);
    }

    private HashMap<Class<? extends Query>, Class<? extends UseCaseQueryHandler>> formatHandlers(
            Set<Class<? extends UseCaseQueryHandler>> queryHandlers) {
        HashMap<Class<? extends Query>, Class<? extends UseCaseQueryHandler>> handlers = new HashMap<>();

        for (Class<? extends UseCaseQueryHandler> handler : queryHandlers) {
            ParameterizedType      paramType  = (ParameterizedType) handler.getGenericInterfaces()[0];
            Class<? extends Query> queryClass = (Class<? extends Query>) paramType.getActualTypeArguments()[0];

            handlers.put(queryClass,
                         handler);
        }

        return handlers;
    }
}
