package com.mock.shared.domain.bus.event;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ofPattern;

public abstract class DomainEvent {

    private final String aggregateId;
    private final String eventId;
    private final String occurredOn;
    private final String version;

    public DomainEvent(String aggregateId) {
        this.aggregateId = aggregateId;
        this.eventId     = UUID.randomUUID()
                               .toString();
        this.occurredOn  = LocalDateTime.now().atOffset(ZoneOffset.UTC)
                                        .format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
        this.version     = "1";
    }

    public DomainEvent(String aggregateId,
                       String eventId,
                       String occurredOn,
                       String version) {
        this.aggregateId = aggregateId;
        this.eventId     = eventId;
        this.occurredOn  = occurredOn;
        this.version     = version;
    }

    protected DomainEvent() {
        this(null);
    }

    public DomainEvent(String aggregateId,
                       String version) {
        this.aggregateId = aggregateId;
        this.version     = version;
        this.eventId     = UUID.randomUUID()
                               .toString();
        this.occurredOn  = LocalDateTime.now().atOffset(ZoneOffset.UTC)
                                        .format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
    }

    public abstract String eventName();

    public abstract HashMap<String, Serializable> toPrimitives();

    public abstract DomainEvent fromPrimitives(String aggregateId,
                                               HashMap<String, Serializable> body,
                                               String eventId,
                                               String occurredOn,
                                               String version);

    public HashMap<String, Serializable> meta() {
        return new HashMap<String, Serializable>() {
            {
                try {
                    put("host",
                        InetAddress.getLocalHost());
                    put("ip",
                        InetAddress.getLocalHost()
                                   .getHostAddress());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public String aggregateId() {
        return aggregateId;
    }

    public String eventId() {
        return eventId;
    }

    public String occurredOn() {
        return occurredOn;
    }

    public String version() {
        return version;
    }
}
