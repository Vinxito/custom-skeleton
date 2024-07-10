package com.mock.shared.domain.mother;

import java.io.Serializable;
import java.util.HashMap;

public class HashMapMother {

    public static HashMap<String, String> create(String key,
                                                 String value) {
        return new HashMap<String, String>() {
            {
                put(key,
                    value);
            }
        };
    }

    public static HashMap<String, Serializable> serializable(String key,
                                                             String value) {
        return new HashMap<String, Serializable>() {
            {
                put(key,
                    value);
            }
        };
    }

    public static HashMap<String, String> random() {
        return new HashMap<String, String>() {
            {
                put(WordMother.random(),
                    WordMother.random());
            }
        };
    }

    public static HashMap<String, Serializable> randomSerializable() {
        return new HashMap<String, Serializable>() {
            {
                put(WordMother.random(),
                    WordMother.random());
            }
        };
    }
}
