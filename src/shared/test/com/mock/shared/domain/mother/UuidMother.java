package com.mock.shared.domain.mother;

import java.util.UUID;

public final class UuidMother {
    public static String random() {
        return UUID.randomUUID().toString();
    }
}
