package com.mock.shared.domain.mother;

public final class EmailMother {

    public static String random() {
        return String.format("%s@%s.%s",
                             WordMother.random(),
                             WordMother.random(),
                             WordMother.random());
    }
}
