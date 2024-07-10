package com.mock.shared.domain.mother;

import java.math.BigDecimal;

public final class BigDecimalMother {
    public static BigDecimal random() {
        return BigDecimal.valueOf(MotherCreator.random().number().randomNumber());
    }
}
