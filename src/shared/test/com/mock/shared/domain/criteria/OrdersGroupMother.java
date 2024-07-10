package com.mock.shared.domain.criteria;

import com.mock.shared.domain.mother.WordMother;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class OrdersGroupMother {

    public static OrdersGroup random() {
        return OrdersGroup.createOne(WordMother.random(),
                                     OrderTypeMother.randomValue());
    }

    public static ArrayList<HashMap<String, Serializable>> randomValues() {
        return random().toPrimitives();
    }
}
