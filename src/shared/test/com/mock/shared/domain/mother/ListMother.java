package com.mock.shared.domain.mother;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

public final class ListMother {
    public static <T> List<T> create(Integer size,
                                     Supplier<T> creator) {
        ArrayList<T> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            list.add(creator.get());
        }

        return list;
    }

    public static ArrayList<?> random(Class<?> clazz,
                                      int qty) {
        ArrayList<Object> list = new ArrayList<>(qty);

        try {
            Method random = clazz.getMethod("random");
            for (int i = 0; i < qty; i++) {
                list.add(random.invoke(clazz));
            }

        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException e) {
            Logger.getLogger("Random method invocation exception: " + e.getMessage());
        }

        return list;
    }

    public static ArrayList<?> randomPrimitives(Class<?> clazz,
                                                int qty) {
        ArrayList<Object> list = new ArrayList<>(qty);

        try {
            Method random = clazz.getMethod("randomPrimitives");
            for (int i = 0; i < qty; i++) {
                list.add(random.invoke(clazz));
            }

        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException e) {
            Logger.getLogger("Random primitives method invocation exception: " + e.getMessage());
        }

        return list;
    }

    public static <T> List<T> random(Supplier<T> creator) {
        return create(IntegerMother.random(),
                      creator);
    }

    public static <T> List<T> one(T element) {
        return Collections.singletonList(element);
    }
}
