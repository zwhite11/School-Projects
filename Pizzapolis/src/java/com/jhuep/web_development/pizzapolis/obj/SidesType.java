package com.jhuep.web_development.pizzapolis.obj;

import com.jhuep.web_development.pizzapolis.entity.Sides;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 8, 2017
 */
public enum SidesType {

    GARLIC_BREAD("Garlic Bread"),
    CHEESE_BREAD("Cheese Bread"),
    BREAD_STICKS("Bread Sticks"),
    FRIES("Fries"),
    WINGS("Wings"),;

    private final String key;

    private SidesType(String name) {
        this.key = name;
    }

    int getId(SidesSizeType size) {
        return (ordinal() * 3) + size.ordinal() + 1;
    }

    public Sides getSideEntity(SidesSizeType size) {
        return new Sides(key, size.toString(), size.getPrice(), new HashSet<>());

    }
    private static Map<String, SidesType> FROM_STRING = new HashMap<>();

    static {
        for (SidesType type : values()) {
            FROM_STRING.put(type.key.toUpperCase(), type);
        }
    }

    public static SidesType fromString(String s) {
        return FROM_STRING.get(s.toUpperCase());
    }
}
