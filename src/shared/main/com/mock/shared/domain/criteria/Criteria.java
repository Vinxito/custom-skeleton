package com.mock.shared.domain.criteria;

import java.io.Serializable;
import java.util.*;

public class Criteria {

    private final Filters        filters;
    private final OrdersGroup    ordersGroup;
    private final CriteriaLimit  limit;
    private final CriteriaOffset offset;

    public Criteria() {
        this.filters     = Filters.empty();
        this.ordersGroup = OrdersGroup.empty();
        this.limit       = CriteriaLimit.none();
        this.offset      = CriteriaOffset.none();
    }

    public Criteria(Filters filters) {
        this.filters     = filters;
        this.ordersGroup = OrdersGroup.empty();
        this.limit       = CriteriaLimit.none();
        this.offset      = CriteriaOffset.none();
    }

    public Criteria(Filters filters,
                    OrdersGroup ordersGroup,
                    Optional<Integer> limit,
                    Optional<Integer> offset) {
        this.filters     = filters;
        this.ordersGroup = ordersGroup;
        this.limit       = CriteriaLimit.create(limit);
        this.offset      = CriteriaOffset.create(offset);
    }

    public Criteria(Filters filters,
                    Optional<Integer> limit) {
        this.filters     = filters;
        this.ordersGroup = OrdersGroup.empty();
        this.limit       = CriteriaLimit.create(limit);
        this.offset      = CriteriaOffset.none();
    }

    public Criteria(Filters filters,
                    OrdersGroup order) {
        this.filters     = filters;
        this.ordersGroup = order;
        this.limit       = CriteriaLimit.none();
        this.offset      = CriteriaOffset.none();
    }

    public static Criteria fromPrimitives(HashMap<String, Serializable> primitives) {
        return new Criteria(Filters.fromPrimitives((HashMap<String, Serializable>) primitives.get("filters")),
                            OrdersGroup.fromPrimitives((ArrayList<HashMap<String, Serializable>>) primitives.get("orders_group")),
                            Optional.ofNullable((Integer) primitives.get("limit")),
                            Optional.ofNullable((Integer) primitives.get("offset")));
    }

    public Filters filters() {
        return filters;
    }

    public boolean hasFilters() {
        return filters.hasFilters();
    }

    public List<Order> orders() {
        return ordersGroup.orders();
    }

    public boolean hasOrders() {
        return ordersGroup.hasOrders();
    }

    public Integer limit() {
        return limit.value();
    }

    public Integer offset() {
        return offset.value();
    }

    public OrdersGroup ordersGroup() {
        return ordersGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Criteria)) return false;

        Criteria criteria = (Criteria) o;

        if (!Objects.equals(filters,
                            criteria.filters)) return false;
        if (!Objects.equals(ordersGroup,
                            criteria.ordersGroup)) return false;
        if (!Objects.equals(limit,
                            criteria.limit)) return false;
        return Objects.equals(offset,
                              criteria.offset);
    }

    @Override
    public int hashCode() {
        int result = filters != null ? filters.hashCode() : 0;
        result = 31 * result + (ordersGroup != null ? ordersGroup.hashCode() : 0);
        result = 31 * result + (limit != null ? limit.hashCode() : 0);
        result = 31 * result + (offset != null ? offset.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s{filters=%s, orderGroup=%s, limit=%s , offset=%s}",
                             Criteria.class.getSimpleName(),
                             filters.toString(),
                             ordersGroup.toString(),
                             limit.toString(),
                             offset.toString());
    }

    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<String, Serializable>() {
            {
                put("filters",
                    filters.toPrimitives());
                put("orders_group",
                    ordersGroup.toPrimitives());
                put("limit",
                    limit.value());
                put("offset",
                    offset.value());
            }
        };
    }
}
