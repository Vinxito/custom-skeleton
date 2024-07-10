package com.mock.shared.domain.mother;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class DateMother {

    public static Date random() {
        return MotherCreator.random().date().birthday();
    }

    public static Date actual() {
        return new Date();
    }

    public static Date from(Integer year,
                            Integer month,
                            Integer day,
                            Integer hour,
                            Integer minute) {
        LocalDateTime predefinedDate = LocalDateTime.of(year,
                                                        month,
                                                        day,
                                                        hour,
                                                        minute);
        return Date.from(predefinedDate.atZone(ZoneId.systemDefault()).toInstant());
    }
}
