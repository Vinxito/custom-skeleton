package com.mock.shared.domain.mother;

public final class LongMother {
    public static Long random() {
        return MotherCreator.random().number().randomNumber();
    }

    public static long between(Long min,
                               Long max) {
        return MotherCreator.random().number().numberBetween(min.intValue(),
                                                             max.intValue());
    }
}
