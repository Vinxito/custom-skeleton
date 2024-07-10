package com.mock.shared.domain.criteria;

import com.mock.shared.domain.value_objects.ValueObject;

import java.util.Objects;
import java.util.Optional;

public class CriteriaLimit extends ValueObject<Integer> {

    private CriteriaLimit(Integer limit) {
        super(limit);
    }

    private static Integer executeRules(Optional<Integer> limit) {
        if (Objects.isNull(limit) || limit.isEmpty() || limit.get() <= 0) {
            return 100000;
        }
        return limit.get();
    }

    public static CriteriaLimit create(Optional<Integer> limit) {
        return new CriteriaLimit(executeRules(limit));
    }

    public static CriteriaLimit none() {
        return new CriteriaLimit(executeRules(Optional.empty()));
    }

    public String serialize() {
        return String.format("limit=%s",
                             value());
    }
}
