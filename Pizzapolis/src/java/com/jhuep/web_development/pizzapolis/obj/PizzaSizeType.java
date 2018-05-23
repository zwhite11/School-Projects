package com.jhuep.web_development.pizzapolis.obj;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 9, 2017
 */
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 9, 2017
 */
public enum PizzaSizeType {

    SMALL("Small", 3, 1.0),
    MEDIUM("Medium", 5, 1.14),
    LARGE("Large", 7, 1.42),
    EXTRA_LARGE("Extra-Large", 10, 1.70),
    EL_GRANDO_MACHISMO("El Grando Machismo: 15 Sqft of taste", 100, 10);
    private final String key;
    private final double price;
    private final double toppingModifier;

    //Cost per topping: 
    //small: 1.58
    //medium: 1.80
    //large: 2.25
    //X-Large: 2.69
    private PizzaSizeType(String name, double price, double toppingModifier) {
        this.key = name;
        this.price = price;
        this.toppingModifier = toppingModifier;
    }

    @Override
    public String toString() {
        return key;
    }

    public double getPizzaPrice() {
        return price;
    }

    public double getToppingsModifier() {
        return 1;
//        return toppingModifier;
    }

    public static Stream<PizzaSizeType> stream() {
        return Arrays.stream(values());
    }

    private static Map<String, PizzaSizeType> FROM_STRING = new HashMap<>();

    static {
        for (PizzaSizeType type : values()) {
            FROM_STRING.put(type.key.toUpperCase(), type);
        }
    }

    public static PizzaSizeType fromString(String s) {
        return FROM_STRING.get(s.toUpperCase());
    }
}
