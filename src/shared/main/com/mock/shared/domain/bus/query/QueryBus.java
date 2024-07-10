package com.mock.shared.domain.bus.query;

public interface QueryBus {
    <R> R ask(Query query);
}
