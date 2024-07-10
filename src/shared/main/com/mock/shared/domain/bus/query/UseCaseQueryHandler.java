package com.mock.shared.domain.bus.query;

public interface UseCaseQueryHandler<Q extends Query, R extends Response> {
    R execute(Q query);
}
