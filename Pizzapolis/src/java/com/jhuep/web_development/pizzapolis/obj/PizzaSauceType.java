package com.jhuep.web_development.pizzapolis.obj;

import com.jhuep.web_development.pizzapolis.entity.PizzaSauce;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 9, 2017
 */
public enum PizzaSauceType {

    TOMATO("Tomato"),
    BBQ_SAUCE("BBQ Sauce"),
    TIKKA_MASALA("Tikka Masala"),
    OLIVE_OIL("Olive Oil"),
    NONE("None"),;
    private final String key;

    private PizzaSauceType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public double getCost() {
        return 5;
    }

    public PizzaSauce getSauce() {
        return new PizzaSauce(key, getCost(), new HashSet<>());
    }

    public boolean isLast() {
        return ordinal() == values().length - 1;
    }
    private static final Map<String, PizzaSauceType> FROM_STRING = new HashMap<>();

    static {
        for (PizzaSauceType type : values()) {
            FROM_STRING.put(type.key.toUpperCase(), type);
        }
        FROM_STRING.put("BBQ", BBQ_SAUCE);
    }

    public static PizzaSauceType fromString(String s) {
        return FROM_STRING.get(s.toUpperCase());
    }
}
