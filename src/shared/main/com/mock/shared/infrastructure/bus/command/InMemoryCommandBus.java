package com.mock.shared.infrastructure.bus.command;

import com.mock.shared.domain.Injectable;
import com.mock.shared.domain.bus.command.Command;
import com.mock.shared.domain.bus.command.CommandBus;
import com.mock.shared.domain.bus.command.UseCaseCommandHandler;
import org.springframework.context.ApplicationContext;

@Injectable
public final class InMemoryCommandBus implements CommandBus {
    private final CommandHandlersMap map;
    private final ApplicationContext context;

    public InMemoryCommandBus(CommandHandlersMap map,
                              ApplicationContext context) {
        this.map     = map;
        this.context = context;
    }

    @Override
    public void dispatch(Command command) {
        Class<? extends UseCaseCommandHandler> commandHandlerClass = map.search(command.getClass());

        UseCaseCommandHandler handler = context.getBean(commandHandlerClass);

        handler.execute(command);

    }
}
