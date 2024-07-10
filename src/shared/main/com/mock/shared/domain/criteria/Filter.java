package com.mock.shared.domain.criteria;

import com.mock.shared.domain.errors.DomainError;

import java.io.Serializable;
import java.util.*;

public final class Filter {
    private final FilterField    field;
    private final FilterOperator operator;
    private final FilterValue    value;
    private final Filters        filters;

    public Filter() {
        this.field    = FilterField.empty();
        this.operator = FilterOperator.EQUAL;
        this.value    = FilterValue.empty();
        this.filters  = Filters.empty();
    }

    public Filter(FilterField field,
                  FilterOperator operator,
                  FilterValue value) {
        this.field    = field;
        this.operator = operator;
        this.value    = value;
        this.filters  = Filters.empty();
    }

    public Filter(FilterField field,
                  FilterOperator operator,
                  FilterValue value,
                  Filters filters) {
        this.field    = field;
        this.operator = operator;
        this.value    = value;
        this.filters  = filters;
    }

    public Filter(Filters filters) {
        this.field    = null;
        this.operator = null;
        this.value    = null;
        this.filters  = filters;
    }


    public static Filter create(String field,
                                String operator,
                                Serializable value) {
        return new Filter(
                new FilterField(field),
                FilterOperator.fromValue(operator.toUpperCase()),
                new FilterValue(value),
                Filters.empty());
    }


    public static Filter create(Filters filters) {
        return new Filter(filters);
    }

    public static Filter fromPrimitives(HashMap<String, Serializable> primitives) {
        Optional<HashMap<String, Serializable>> filtersPrimitives =
                Optional.ofNullable((HashMap<String, Serializable>) primitives.get("filters"));
        if (Objects.nonNull(primitives.get("field"))) {
            return new Filter(new FilterField((String) primitives.get("field")),
                              FilterOperator.fromValue((String) primitives.get("operator")),
                              new FilterValue(primitives.get("value")),
                              filtersPrimitives
                                      .map(filters -> Filters.fromPrimitives(filters))
                                      .orElse(Filters.empty()));
        }

        return filtersPrimitives
                .map(filters -> new Filter(Filters.fromPrimitives(filters)))
                .orElseThrow(InvalidFilter::new);
    }

    public Boolean fieldEquals(FilterField field) {
        return this.fieldValue().equals(field.value());
    }

    public FilterField field() {
        return field;
    }

    public String fieldValue() {
        return field.value();
    }

    public FilterOperator operator() {
        return operator;
    }

    public String operatorValue() {
        return operator.value();
    }

    public FilterValue value() {
        return value;
    }

    public Serializable valueValue() {
        return value.value();
    }

    public boolean hasFilters() {
        return filters.hasFilters();
    }

    public boolean hasFilter() {
        return Objects.nonNull(field) && (Objects.nonNull(value));
    }

    public Filters filters() {
        return filters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filter filter = (Filter) o;
        return Objects.equals(field,
                              filter.field)
               && operator == filter.operator
               && Objects.equals(value,
                                 filter.value)
               && Objects.equals(filters,
                                 filter.filters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field,
                            operator,
                            value,
                            filters);
    }

    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<String, Serializable>() {
            {
                Optional.ofNullable(field).ifPresent(filters -> put("field",
                                                                    field.value()));
                Optional.ofNullable(operator).ifPresent(filters -> put("operator",
                                                                       operator.value()));
                Optional.ofNullable(value).ifPresent(filters -> put("value",
                                                                    value.value()));
                Optional.ofNullable(filters).ifPresent(filters -> put("filters",
                                                                      filters.toPrimitives()));
            }
        };
    }

    @Override
    public String toString() {
        if (hasFilter()) {
            return String.format("%s{ field= %s, operator= %s, value= %s}",
                                 Filter.class.getSimpleName(),
                                 field.value(),
                                 operator.value(),
                                 value.value());
        }
        return String.format("%s{ filters= [%s]}",
                             Filter.class.getSimpleName(),
                             filters.toString());
    }


    public List<Filter> getAllFinalFiltersAndValues() {
        List<Filter> result = new ArrayList<>();
        if (Objects.nonNull(operator)) {
            result.add(this);
        }

        result.addAll(filters.allFinalFiltersAndValues());
        return result;
    }


    public static class InvalidFilter extends DomainError {

        public InvalidFilter() {
            super("InvalidFilter",
                  "Filter require a field or array of filters");
        }
    }
}
