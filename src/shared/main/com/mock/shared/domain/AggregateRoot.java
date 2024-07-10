package com.mock.shared.domain;

import com.mock.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class AggregateRoot {
    private List<DomainEvent> domainEvents = new ArrayList<>();

    final public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = domainEvents;

        domainEvents = Collections.emptyList();

        return events;
    }

    final protected void record(DomainEvent event) {
        domainEvents.add(event);
    }

    public abstract Map<String, Serializable> toPrimitives();

}
