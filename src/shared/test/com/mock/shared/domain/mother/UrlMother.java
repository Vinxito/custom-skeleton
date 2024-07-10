package com.mock.shared.domain.mother;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlMother {

    public static URL withWord(String url) {
        try {
            return new URL(String.format("http://%s.com",
                                         url));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static URL random() {
        return withWord(WordMother.random());
    }

    public static String asString() {
        return withWord(WordMother.random()).toString();
    }
}
