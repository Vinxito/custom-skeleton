package com.mock.shared.domain.criteria;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public final class Order {

    private final OrderBy   orderBy;
    private final OrderType orderType;

    private Order() {
        this.orderBy   = OrderBy.empty();
        this.orderType = OrderType.NONE;
    }

    private Order(OrderBy orderBy,
                  OrderType orderType) {
        this.orderBy   = orderBy;
        this.orderType = orderType;
    }

    public static Order create(String orderBy,
                               String orderType) {
        OrderType orderTypeCreated = Optional.ofNullable(orderType)
                                             .map(OrderType::fromValue)
                                             .orElse(OrderType.ASC);
        return new Order(OrderBy.create(orderBy),
                         orderTypeCreated);
    }

    public static Order none() {
        return new Order(new OrderBy(""),
                         OrderType.NONE);
    }

    public static Order desc(String orderBy) {
        return new Order(new OrderBy(orderBy.toUpperCase()),
                         OrderType.DESC);
    }

    public static Order asc(String orderBy) {
        return new Order(new OrderBy(orderBy.toUpperCase()),
                         OrderType.ASC);
    }

    public static Order fromPrimitives(HashMap<String, Serializable> primitives) {
        return new Order(new OrderBy((String) primitives.get("order_by")),
                         OrderType.fromValue((String) primitives.get("order_type")));
    }

    public String orderByValue() {
        return orderBy.value();
    }

    public OrderType orderType() {
        return orderType;
    }

    public boolean hasOrder() {
        return !orderType.isNone();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Order order = (Order) o;
        return Objects.equals(orderBy,
                              order.orderBy) && orderType == order.orderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderBy,
                            orderType);
    }

    @Override
    public String toString() {
        return String.format("%s{orderBy=%s , orderType=%s}",
                             Order.class.getSimpleName(),
                             orderBy.value(),
                             orderType.value());
    }

    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<String, Serializable>() {
            {
                put("order_by",
                    orderBy.value());
                put("order_type",
                    orderType.value());
            }
        };
    }
}
