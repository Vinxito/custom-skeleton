package com.mock.shared.domain.criteria;

public class FilterMother {

    public static Filter createContainsFilter(String filterField,
                                              String filterValue) {
        return Filter.create(filterField,
                             FilterOperator.CONTAINS.value(),
                             filterValue);
    }

    public static Filter equalsFilter(String filterField,
                                      String filterValue) {
        return Filter.create(filterField,
                             FilterOperator.EQUAL.value(),
                             filterValue);
    }
}
