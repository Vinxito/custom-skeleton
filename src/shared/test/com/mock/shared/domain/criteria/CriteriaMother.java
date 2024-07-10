package com.mock.shared.domain.criteria;

import com.mock.shared.infrastructure.web.CriteriaParserFilterMother;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CriteriaMother {

    public static Criteria random() {
        return new Criteria(FiltersMother.random(),
                            OrdersGroupMother.random());
    }

    public static HashMap<String, Serializable> randomValues() {
        return random().toPrimitives();
    }

    public static Criteria create(List<Filter> filters,
                                  Order order) {
        return new Criteria(new Filters(filters),
                            OrdersGroup.create(Collections.singletonList(order)));
    }

    public static Criteria create(List<Filter> filters) {
        return new Criteria(new Filters(filters),
                            OrdersGroup.empty());
    }

    public static String limitAndOffsetValues(Integer limit,
                                              Integer offset) {

        List<String> queryParams = new ArrayList<>();

        queryParams.add(String.format("limit=%s",
                                      limit));
        queryParams.add(String.format("offset=%s",
                                      offset));

        return String.join("&",
                           queryParams);
    }

    public static String toUriParams(Integer limit,
                                     Integer offset,
                                     Filters filters,
                                     String orderBy,
                                     String orderType) {

        List<String> queryParams = new ArrayList<>();

        queryParams.add(CriteriaMother.limitAndOffsetValues(limit,
                                                            offset));

        queryParams.add(CriteriaParserFilterMother.toString(filters));

        queryParams.add(OrderMother.create(orderBy,
                                           orderType));

        return String.join("&",
                           queryParams);
    }

}
