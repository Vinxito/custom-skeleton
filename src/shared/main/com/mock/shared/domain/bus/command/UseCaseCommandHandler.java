package com.mock.shared.domain.bus.command;

public interface UseCaseCommandHandler<T extends Command> {
    void execute(T command);
}
