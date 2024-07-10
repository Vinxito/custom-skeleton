package com.mock.shared.domain.mother;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public final class TimestampMother {
    public static Timestamp random() {
        return new Timestamp(MotherCreator.random().date().future(IntegerMother.randomBetweenTwoValues(1,
                                                                                                       5),
                                                                  TimeUnit.DAYS).getTime());
    }
}
