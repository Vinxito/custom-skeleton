package com.mock.shared.domain.mother;

public final class DoubleMother {
    public static Double random() {
        return (double) MotherCreator.random().number().randomNumber();
    }

    public static Double randomPercentage() {
        return (double) MotherCreator.random()
                                     .number()
                                     .numberBetween(0,
                                                    100);
    }
}
