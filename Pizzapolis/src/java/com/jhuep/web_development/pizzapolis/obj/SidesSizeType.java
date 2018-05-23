package com.jhuep.web_development.pizzapolis.obj;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 9, 2017
 */
public enum SidesSizeType {

    SMALL("Small", 3),
    MEDIUM("Medium", 5),
    LARGE("Large", 7);
    private final String key;
    private final double price;

    private SidesSizeType(String name, double price) {
        this.key = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return key;
    }

    public double getPrice() {
        return price;
    }

    public static Stream<SidesSizeType> stream() {
        return Arrays.stream(values());
    }

    private static Map<String, SidesSizeType> FROM_STRING = new HashMap<>();

    static {
        for (SidesSizeType type : values()) {
            FROM_STRING.put(type.key.toUpperCase(), type);
        }
    }

    public static SidesSizeType fromString(String s) {
        return FROM_STRING.get(s.toUpperCase());
    }

}
