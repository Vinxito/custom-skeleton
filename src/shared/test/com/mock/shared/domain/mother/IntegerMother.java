package com.mock.shared.domain.mother;

public final class IntegerMother {
    public static Integer random() {
        return MotherCreator.random().number().randomDigit();
    }

    public static Integer randomBetweenTwoValues(Integer firstValue,
                                                 Integer secondValue) {
        return MotherCreator.random()
                            .number()
                            .numberBetween(firstValue,
                                           secondValue);
    }

    public static Integer randomPercentage() {
        return MotherCreator.random()
                            .number()
                            .numberBetween(0,
                                           100);
    }
}
