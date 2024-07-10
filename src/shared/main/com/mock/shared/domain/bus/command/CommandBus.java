package com.mock.shared.domain.bus.command;

public interface CommandBus {
    void dispatch(Command command);
}
