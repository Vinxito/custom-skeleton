package com.mock.shared.domain.criteria;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public final class Filters {

    private final List<Filter> filters;
    private final FilterType   type;

    public Filters() {
        this.filters = new ArrayList<>();
        this.type    = FilterType.AND;
    }

    public Filters(List<Filter> filters) {
        this.filters = filters;
        this.type    = FilterType.AND;
    }

    public Filters(Filter filter) {
        this.filters = Collections.singletonList(filter);
        this.type    = FilterType.AND;
    }

    public Filters(List<Filter> filters,
                   FilterType type) {
        this.filters = filters;
        this.type    = type;
    }

    public static Filters empty() {
        return new Filters();
    }

    public static Filters none() {
        return new Filters(Collections.emptyList());
    }

    public static Filters substituteFilterValue(Filters filters,
                                                Filter filter) {
        return new Filters(substituteFilterValue(filters.all(),
                                                 filter),
                           filters.type());
    }

    private static List<Filter> substituteFilterValue(List<Filter> filters,
                                                      Filter substituteFilter) {
        List<Filter> filtersList = new ArrayList<>();

        filters.forEach(filter -> {
            if (filter.hasFilter() && !filter.hasFilters()) {
                if (filter.fieldEquals(substituteFilter.field())) {
                    filter = substituteFilter;
                }

                filtersList.add(filter);
            }

            if (filter.hasFilter() && filter.hasFilters()) {
                filtersList.add(new Filter(filter.field(),
                                           filter.operator(),
                                           filter.value(),
                                           substituteFilterValue(filter.filters(),
                                                                 substituteFilter)));
            }

            if (!filter.hasFilter()) {
                filtersList.add(new Filter(substituteFilterValue(filter.filters(),
                                                                 substituteFilter)));
            }
        });

        return filtersList;
    }

    public static Filters fromPrimitives(HashMap<String, Serializable> primitives) {
        ArrayList<HashMap<String, Serializable>> filters =
                (ArrayList<HashMap<String, Serializable>>) primitives.getOrDefault("filters",
                                                                                   new ArrayList<>());
        return new Filters(
                filters.stream().map(Filter::fromPrimitives).collect(Collectors.toList()),
                FilterType.fromValue((String) primitives.get("type"))
        );
    }

    public List<Filter> all() {
        return filters;
    }

    public boolean hasFilters() {
        return !filters.isEmpty();
    }

    public FilterType type() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filters filters1 = (Filters) o;
        return Objects.equals(filters,
                              filters1.filters)
               && Objects.equals(type,
                                 filters1.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filters,
                            type);
    }

    @Override
    public String toString() {
        return String.format("%s{filters= [%s] , filterType= %s }",
                             Filters.class.getSimpleName(),
                             filters.stream().map(Objects::toString).collect(Collectors.joining(",")),
                             type.toString());
    }

    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<String, Serializable>() {
            {
                put("filters",
                    filters.stream().map(Filter::toPrimitives).collect(Collectors.toCollection(ArrayList::new)));
                put("type",
                    type.value());
            }
        };
    }


    public List<Filter> allFinalFiltersAndValues() {
        return filters.stream().map(Filter::getAllFinalFiltersAndValues).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
