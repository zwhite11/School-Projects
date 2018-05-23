package com.jhuep.web_development.pizzapolis.database;

import com.jhuep.web_development.pizzapolis.util.DbUtils;
import com.jhuep.web_development.pizzapolis.entity.Customer;
import com.jhuep.web_development.pizzapolis.entity.PizzaCrust;
import com.jhuep.web_development.pizzapolis.entity.PizzaSauce;
import com.jhuep.web_development.pizzapolis.entity.Sides;
import com.jhuep.web_development.pizzapolis.entity.Toppings;
import com.jhuep.web_development.pizzapolis.obj.PizzaCoverageType;
import com.jhuep.web_development.pizzapolis.obj.PizzaCrustType;
import com.jhuep.web_development.pizzapolis.obj.PizzaSauceType;
import com.jhuep.web_development.pizzapolis.obj.SidesSizeType;
import com.jhuep.web_development.pizzapolis.obj.ToppingsType;
import com.jhuep.web_development.pizzapolis.obj.SidesType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.hibernate.Session;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 9, 2017
 */
public class RetrieveHelper {

    private static List<Toppings> allToppings = null;
    private static Map<String, Toppings> toppingsMap = null;

    private static List<Sides> allSides = null;
    private static Map<String, Sides> sidesMap = null;

    private static List<PizzaSauce> allSauces = null;
    private static Map<PizzaSauceType, PizzaSauce> sauceMap = null;

    private static List<PizzaCrust> allCrusts = null;
    private static Map<PizzaCrustType, PizzaCrust> crustMap = null;

    public static Toppings getTopping(ToppingsType topping, PizzaCoverageType coverage) {
        if (toppingsMap == null) {
            toppingsMap = retrieveAllToppings().stream().collect(Collectors.toMap(
                    t -> t.getName() + "-" + t.getPizzaCoverage(), //Key
                    Function.identity() //Value
            ));
        }
        return toppingsMap.get(topping.getKey() + "-" + coverage.getKey());
    }

    public static boolean doesCustomerLoginExist(String loginName) {
        return retrieveAll(Customer.class)
                .stream()
                .filter(c -> c.getLogin().equalsIgnoreCase(loginName))
                .findFirst()
                .isPresent();
    }

    public static Customer retrieveCustomer(String loginName, String password) {
        return retrieveAll(Customer.class).stream()
                .filter(c -> c.getLogin().equalsIgnoreCase(loginName) && c.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    public static List<Toppings> retrieveAllToppings() {
        if (allToppings == null) {
            allToppings = retrieveAll(Toppings.class);
        }
        return allToppings;
    }

    public static Sides getSide(SidesType side, SidesSizeType size) {
        if (sidesMap == null) {
            sidesMap = retrieveAllSides().stream()
                    .collect(Collectors.toMap(
                                    t -> SidesType.fromString(t.getName()) + "-" + t.getSize(), //Key
                                    Function.identity() //Value
                            ));
        }
        return sidesMap.get(side + "-" + size.toString());
    }

    public static List<Sides> retrieveAllSides() {
        if (allSides == null) {
            allSides = retrieveAll(Sides.class);
        }
        return allSides;
    }

    public static PizzaSauce getSauce(PizzaSauceType sauce) {
        if (sauceMap == null) {
            sauceMap = retrieveAllSauces().stream().collect(Collectors.toMap(
                    t -> PizzaSauceType.fromString(t.getSauceName()), //Key
                    Function.identity() //Value
            ));
        }
        return sauceMap.get(sauce);
    }

    public static List<PizzaSauce> retrieveAllSauces() {
        if (allSauces == null) {
            allSauces = retrieveAll(PizzaSauce.class);
        }
        return allSauces;
    }

    public static PizzaCrust getCrust(PizzaCrustType crust) {
        if (crustMap == null) {
            crustMap = retrieveAllCrusts().stream().collect(Collectors.toMap(
                    t -> PizzaCrustType.fromString(t.getCrustName()), //Key
                    Function.identity() //Value
            ));
        }
        return crustMap.get(crust);
    }

    public static List<PizzaCrust> retrieveAllCrusts() {
        if (allCrusts == null) {
            allCrusts = retrieveAll(PizzaCrust.class);
        }
        return allCrusts;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> retrieveAll(Session session, Class<T> clazz) {
        return (List<T>) session.createCriteria(clazz).list();
    }

    public static <T> List<T> retrieveAll(Class<T> clazz) {
        List<T> result = new ArrayList<>();
        DbUtils.inTransaction(s -> {
            result.addAll(retrieveAll(s, clazz));
        });
        return Collections.unmodifiableList(result);
    }

}
