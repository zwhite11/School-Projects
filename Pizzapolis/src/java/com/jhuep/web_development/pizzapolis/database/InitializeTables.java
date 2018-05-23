package com.jhuep.web_development.pizzapolis.database;

import com.jhuep.web_development.pizzapolis.util.DbUtils;
import com.jhuep.web_development.pizzapolis.entity.PizzaCrust;
import com.jhuep.web_development.pizzapolis.entity.PizzaSauce;
import com.jhuep.web_development.pizzapolis.entity.Sides;
import com.jhuep.web_development.pizzapolis.entity.Toppings;
import com.jhuep.web_development.pizzapolis.obj.SidesSizeType;
import com.jhuep.web_development.pizzapolis.obj.PizzaCoverageType;
import com.jhuep.web_development.pizzapolis.obj.PizzaCrustType;
import com.jhuep.web_development.pizzapolis.obj.PizzaSauceType;
import com.jhuep.web_development.pizzapolis.obj.ToppingsType;
import com.jhuep.web_development.pizzapolis.obj.SidesType;
import java.util.Arrays;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 8, 2017
 */
public class InitializeTables {

    public static void main(String... args) throws Exception {
        initTables();
        DbUtils.getSessionFactory().close();
    }

    public static void initTables() {
        DbUtils.inTransaction(session -> {
            initSides(session);
            initCrust(session);
            initToppings(session);
            initSauces(session);
        });
    }

    public static void initCrust(Session session) {
        List<PizzaCrust> persisted = RetrieveHelper.retrieveAllCrusts();
        Arrays.stream(PizzaCrustType.values())
                .map(PizzaCrustType::createCrust)
                .filter(current -> persisted.contains(current) == false)
                .forEachOrdered(session::saveOrUpdate);
    }

    public static void initSides(Session session) {
        List<Sides> persisted = RetrieveHelper.retrieveAllSides();
        Arrays.stream(SidesType.values())
                .flatMap(side -> SidesSizeType.stream().map(size -> side.getSideEntity(size)))
                .filter(current -> persisted.contains(current) == false)
                .forEachOrdered(session::saveOrUpdate);
    }

    public static void initToppings(Session session) {
        List<Toppings> persisted = RetrieveHelper.retrieveAllToppings();
        Arrays.stream(ToppingsType.values())
                .flatMap(topping -> PizzaCoverageType.stream().map(coverage -> topping.getTopping(coverage)))
                .filter(current -> persisted.contains(current) == false)
                .forEachOrdered(session::persist);
    }

    private static void initSauces(Session session) {
        List<PizzaSauce> persisted = RetrieveHelper.retrieveAllSauces();
        Arrays.stream(PizzaSauceType.values())
                .map(PizzaSauceType::getSauce)
                .filter(current -> persisted.contains(current) == false)
                .forEachOrdered(session::saveOrUpdate);
    }

}
