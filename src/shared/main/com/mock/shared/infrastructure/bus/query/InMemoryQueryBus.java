package com.mock.shared.infrastructure.bus.query;

import com.mock.shared.domain.Injectable;
import com.mock.shared.domain.bus.query.Query;
import com.mock.shared.domain.bus.query.QueryBus;
import com.mock.shared.domain.bus.query.Response;
import com.mock.shared.domain.bus.query.UseCaseQueryHandler;
import org.springframework.context.ApplicationContext;

@Injectable
public final class InMemoryQueryBus implements QueryBus {
    private final QueryHandlersMap   map;
    private final ApplicationContext context;

    public InMemoryQueryBus(QueryHandlersMap map,
                            ApplicationContext context) {
        this.map     = map;
        this.context = context;
    }

    @Override
    public Response ask(Query query) {
        Class<? extends UseCaseQueryHandler> queryHandlerClass = map.search(query.getClass());

        UseCaseQueryHandler handler = context.getBean(queryHandlerClass);

        return handler.execute(query);
    }
}
