package com.mock.shared.domain.mother;

public class SentenceMother {
    public static String random() {
        return MotherCreator.random().lorem().sentence();
    }

    public static String randomLimit(int wordSize) {
        return MotherCreator.random().lorem().sentence(wordSize);
    }
}
