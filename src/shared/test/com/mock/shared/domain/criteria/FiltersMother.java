package com.mock.shared.domain.criteria;

import com.mock.shared.domain.mother.WordMother;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FiltersMother {

    public static Filters create(String filterField,
                                 String filterOperator,
                                 String filterValue) {
        return new Filters(List.of(Filter.create(filterField,
                                                 filterOperator,
                                                 filterValue)));
    }

    public static Filters contains(String filterField,
                                   String filterValue) {
        return new Filters(Filter.create(filterField,
                                         FilterOperator.CONTAINS.value(),
                                         filterValue));
    }

    public static Filters random() {
        return new Filters(Filter.create(WordMother.random(),
                                         FilterOperator.CONTAINS.value(),
                                         WordMother.random()));
    }

    public static Filters withId() {
        return new Filters(Filter.create("id",
                                         FilterOperator.EQUAL.value(),
                                         WordMother.random()));
    }

    public static HashMap<String, Serializable> randomValues() {
        return random().toPrimitives();
    }

    public static HashMap<String, Serializable> withIdValues() {
        return withId().toPrimitives();
    }
}
