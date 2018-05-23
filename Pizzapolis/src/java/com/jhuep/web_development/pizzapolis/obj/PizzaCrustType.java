package com.jhuep.web_development.pizzapolis.obj;

import com.jhuep.web_development.pizzapolis.entity.PizzaCrust;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 9, 2017
 */
public enum PizzaCrustType {

    THIN("Thin", 5),
    HAND_TOSSED("Hand Tossed", 5),
    CHEESE_CRUST("Cheese Crust", 7),
    GLUTEN_FREE("Gluten Free", 8),
    WHOLE_GRAIN("Whole Grain", 8),
    CHICAGO_STYLE("Chicago Style", 9),;
    private final String description;
    private final double price;

    private PizzaCrustType(String description, double price) {
        this.description = description;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    private static Map<String, PizzaCrustType> FROM_STRING = new HashMap<>();

    static {
        for (PizzaCrustType type : values()) {
            FROM_STRING.put(type.description.toUpperCase(), type);
        }
    }

    public PizzaCrust createCrust() {
        return new PizzaCrust(description, price, new HashSet<>());
    }

    public static PizzaCrustType fromString(String s) {
        return FROM_STRING.get(s.toUpperCase());
    }
}
