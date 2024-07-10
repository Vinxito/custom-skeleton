package com.mock.shared.infrastructure.web;

import com.mock.shared.domain.criteria.Filter;
import com.mock.shared.domain.criteria.Filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CriteriaParserFilterMother {

    public static List<HashMap<String, String>> create(Filters filters) {

        List<HashMap<String, String>> filtersDeserialized = new ArrayList<>();

        for (int possibleFilterKey = 0; possibleFilterKey < filters.all().size(); possibleFilterKey++) {

            Filter filter = filters.all().get(possibleFilterKey);

            filtersDeserialized.add(new HashMap<String, String>() {
                {
                    put("field",
                        String.format("filters[%s][field]",
                                      filter.fieldValue()));
                    put("operator",
                        String.format("filters[%s][operator]",
                                      filter.operator().value()));
                    put("value",
                        String.format("filters[%s][value]",
                                      filter.valueValue()));
                }
            });

        }

        return filtersDeserialized;
    }


    public static String toString(Filters filters) {

        List<String> filtersDeserialized = new ArrayList<>();

        for (int possibleFilterKey = 0; possibleFilterKey < filters.all().size(); possibleFilterKey++) {

            Filter filter = filters.all().get(possibleFilterKey);

            filtersDeserialized.add(String.format("filters[%s][field]=%s",
                                                  possibleFilterKey,
                                                  filter.fieldValue()));
            filtersDeserialized.add(String.format("filters[%s][operator]=%s",
                                                  possibleFilterKey,
                                                  filter.operator().value()));
            filtersDeserialized.add(String.format("filters[%s][value]=%s",
                                                  possibleFilterKey,
                                                  filter.valueValue()));

        }

        return String.join("&",
                           filtersDeserialized);
    }
}
