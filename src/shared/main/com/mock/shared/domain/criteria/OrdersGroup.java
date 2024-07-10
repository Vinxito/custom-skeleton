package com.mock.shared.domain.criteria;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class OrdersGroup {
    private final List<Order> orders;

    private OrdersGroup() {
        this.orders = new ArrayList<>();
    }

    private OrdersGroup(List<Order> orders) {
        this.orders = orders;
    }

    public static OrdersGroup create(List<Order> orders) {
        return new OrdersGroup(orders);
    }

    public static OrdersGroup createOne(String orderBy,
                                        String orderType) {
        if (Objects.isNull(orderBy)) {
            return empty();
        }
        return new OrdersGroup(Collections.singletonList(Order.create(orderBy,
                                                                      orderType)));
    }

    public static OrdersGroup empty() {
        return new OrdersGroup(new ArrayList<>());
    }

    public static OrdersGroup fromPrimitives(ArrayList<HashMap<String, Serializable>> primitives) {
        return new OrdersGroup(primitives.stream().map(Order::fromPrimitives).collect(Collectors.toList()));
    }

    public boolean hasOrders() {
        return !orders.isEmpty();
    }

    public List<Order> orders() {
        return orders.stream()
                     .filter(order -> !order.orderType().isNone())
                     .collect(Collectors.toList());
    }

    public ArrayList<HashMap<String, Serializable>> toPrimitives() {
        return orders.stream().map(Order::toPrimitives).collect(Collectors.toCollection(ArrayList::new));

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrdersGroup)) return false;
        OrdersGroup that = (OrdersGroup) o;
        return Objects.equals(orders,
                              that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders);
    }

    @Override
    public String toString() {
        return String.format("%s{orders=[%s]}",
                             OrdersGroup.class.getSimpleName(),
                             orders.stream().map(Objects::toString).collect(Collectors.joining(",")));
    }


}
