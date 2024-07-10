package com.mock.shared.domain.mother;

public class FloatMother {

    public static Float random() {
        return ((Long) MotherCreator.random().number().randomNumber()).floatValue();
    }
}
