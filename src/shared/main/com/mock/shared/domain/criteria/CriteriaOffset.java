package com.mock.shared.domain.criteria;

import com.mock.shared.domain.value_objects.ValueObject;

import java.util.Objects;
import java.util.Optional;

public class CriteriaOffset extends ValueObject<Integer> {

    private CriteriaOffset(Integer offset) {
        super(offset);
    }

    private static Integer executeRules(Optional<Integer> offset) {
        if (Objects.isNull(offset) || offset.isEmpty() || offset.get() <= 0) {
            return 0;
        }
        return offset.get();
    }

    public static CriteriaOffset create(Optional<Integer> offset) {
        return new CriteriaOffset(executeRules(offset));
    }

    public static CriteriaOffset none() {
        return new CriteriaOffset(executeRules(Optional.empty()));
    }

    public String serialize() {
        return String.format("offset=%s",
                             value());
    }
}
