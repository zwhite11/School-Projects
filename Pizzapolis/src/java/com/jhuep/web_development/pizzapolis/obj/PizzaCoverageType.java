package com.jhuep.web_development.pizzapolis.obj;

import com.jhuep.web_development.pizzapolis.entity.Toppings;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 9, 2017
 */
public enum PizzaCoverageType {

    LEFT("Left", 0.5),
    WHOLE("Whole", 1),
    RIGHT("Right", 0.5),;

    public static Stream<PizzaCoverageType> stream() {
        return Arrays.stream(values());
    }

    private final String key;
    private final double costModifier;

    private PizzaCoverageType(String key, double costModifier) {
        this.key = key;
        this.costModifier = costModifier;
    }

    public String getKey() {
        return key;
    }

    public boolean isLast() {
        return ordinal() == values().length - 1;
    }

    public double getCostModifier() {
//        return costModifier;
        return 1;
    }

    private static Map<String, PizzaCoverageType> FROM_STRING = new HashMap<>();

    static {
        for (PizzaCoverageType type : values()) {
            FROM_STRING.put(type.key.toUpperCase(), type);
        }
    }

    public static PizzaCoverageType fromString(String s) {
        return FROM_STRING.get(s.toUpperCase());
    }

    public String join(Collection<Toppings> toJoin) {
        return key + " Toppings: " + toJoin.stream()
                .map(t -> t.getName())
                .collect(Collectors.joining(", "));
    }

}
