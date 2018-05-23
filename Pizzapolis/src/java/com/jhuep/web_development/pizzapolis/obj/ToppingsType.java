package com.jhuep.web_development.pizzapolis.obj;

import com.jhuep.web_development.pizzapolis.entity.Toppings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 9, 2017
 */
public enum ToppingsType {

    PEPPERONI("Pepperoni", 2),
    SAUSAGE("Sausage", 2),
    CHICKEN("Chicken", 2),
    SHRIMP("Shrimp", 2),
    GROUND_BEEF("Ground Beef", 2),
    BACON("Bacon", 2),
    HAM("Ham", 2),
    ONIONS("Onions", 1),
    TOMATOES("Tomatoes", 1),
    MUSHROOMS("Mushrooms", 1),
    PINEAPPLE("Pineapple", 1),
    JALAPENOS("Jalapenos", 1),
    GREEN_PEPPERS("Green Peppers", 1),
    BANANA_PEPPERS("Banana Peppers", 1),
    SPINACH("Spinach", 1),
    OLIVES("Olives", 1),
    EXTRA_CHEESE("Extra Cheese", 1),;
    private final String key;
    private final double cost;

    private ToppingsType(String key, double cost) {
        this.key = key;
        this.cost = cost;
    }

    public String getKey() {
        return key;
    }

    public double getCost() {
        return cost;
    }

//    private int getId(PizzaCoverage coverage) {
//        return (ordinal() * 3) + coverage.ordinal() + 1;
//    }
    public Toppings getTopping(PizzaCoverageType coverage) {
        return new Toppings(/*getId(coverage),*/key, cost, coverage.getKey(), new HashSet<>());
    }

    public boolean isLast() {
        return ordinal() == values().length - 1;
    }
    private static Map<String, ToppingsType> FROM_STRING = new HashMap<>();

    static {
        for (ToppingsType type : values()) {
            FROM_STRING.put(type.key.toUpperCase(), type);
        }
    }

    public static ToppingsType fromString(String s) {

        ToppingsType result = FROM_STRING.get(s.toUpperCase());
        if (result == null) {
            System.out.println("No ToppingsType found for '" + s + "'.");
        }
        return result;
    }
}
