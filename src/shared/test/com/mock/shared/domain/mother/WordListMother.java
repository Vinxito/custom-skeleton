package com.mock.shared.domain.mother;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordListMother {

    public static List<String> random() {
        return new ArrayList<>(Arrays.asList(WordMother.random(),
                                             WordMother.random()));
    }
}
