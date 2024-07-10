package com.mock.shared.domain.mother;

public final class WordMother {
    public static String random() {
        return MotherCreator.random().lorem().word();
    }
}
