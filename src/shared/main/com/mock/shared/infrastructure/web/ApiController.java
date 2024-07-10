package com.mock.shared.infrastructure.web;

import com.mock.shared.domain.bus.command.Command;
import com.mock.shared.domain.bus.command.CommandBus;
import com.mock.shared.domain.bus.query.Query;
import com.mock.shared.domain.bus.query.QueryBus;

public abstract class ApiController {
    private final QueryBus   queryBus;
    private final CommandBus commandBus;

    public ApiController(QueryBus queryBus,
                         CommandBus commandBus) {
        this.queryBus   = queryBus;
        this.commandBus = commandBus;
    }

    protected void dispatch(Command command) {
        commandBus.dispatch(command);
    }

    protected <R> R ask(Query query) {
        return queryBus.ask(query);
    }

}
