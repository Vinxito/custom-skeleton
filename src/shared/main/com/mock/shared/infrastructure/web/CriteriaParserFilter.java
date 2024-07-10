package com.mock.shared.infrastructure.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CriteriaParserFilter {

    public static List<HashMap<String, Serializable>> parseFilters(HashMap<String, Serializable> params) {
        int maxParams = params.size();

        List<HashMap<String, Serializable>> filters = new ArrayList<>();

        for (int possibleFilterKey = 0; possibleFilterKey < maxParams; possibleFilterKey++) {
            if (params.containsKey(String.format("filters[%s][field]",
                                                 possibleFilterKey))) {
                int key = possibleFilterKey;

                filters.add(new HashMap<String, Serializable>() {
                    {
                        put("field",
                            params.get(String.format("filters[%s][field]",
                                                     key)));
                        put("operator",
                            params.get(String.format("filters[%s][operator]",
                                                     key)));
                        put("value",
                            params.get(String.format("filters[%s][value]",
                                                     key)));
                        put("type",
                            "AND");
                    }
                });
            }
        }

        return filters;
    }

    public static Optional<Integer> parseLimit(HashMap<String, Serializable> params) {
        return Optional.ofNullable(params.get("limit"))
                       .map(Object::toString)
                       .map(Integer::parseInt);
    }

    public static Optional<Integer> parseOffset(HashMap<String, Serializable> params) {
        return Optional.ofNullable(params.get("offset"))
                       .map(Object::toString)
                       .map(Integer::parseInt);
    }

    public static Optional<String> parseOrderby(HashMap<String, Serializable> params) {
        return Optional.ofNullable((String) params.get("order_by"));
    }

    public static Optional<String> parseOrder(HashMap<String, Serializable> params) {
        return Optional.ofNullable((String) params.get("order"));
    }
}
