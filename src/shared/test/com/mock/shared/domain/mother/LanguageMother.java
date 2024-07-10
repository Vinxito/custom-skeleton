package com.mock.shared.domain.mother;

import java.util.List;

public class LanguageMother {

    private static final List<String> LANGUAGES = List.of("ES",
                                                          "EN");

    public static String random() {
        return LANGUAGES.get(IntegerMother.randomBetweenTwoValues(0,
                                                                  LANGUAGES.size()));
    }
}
