package com.mock.shared.domain.criteria;

import java.util.ArrayList;
import java.util.List;

public class OrderMother {

    public static String create(String orderBy,
                                String orderType) {

        List<String> orders = new ArrayList<>();

        orders.add(String.format("order_by=%s",
                                 orderBy));
        orders.add(String.format("order=%s",
                                 orderType));

        return String.join("&",
                           orders);
    }
}
