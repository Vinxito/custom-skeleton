package com.mock.shared.domain.criteria.specification;

import com.mock.shared.domain.criteria.*;
import com.mock.shared.domain.value_objects.Identifier;

import java.util.Optional;

public final class CriteriaFilterById extends Criteria {

    public CriteriaFilterById(Identifier id) {
        super(new Filters(new Filter(new FilterField("id"),
                                     FilterOperator.EQUAL,
                                     new FilterValue(id.value()))),
              Optional.of(1));
    }
}
