package com.jhuep.web_development.pizzapolis.database;

import com.jhuep.web_development.pizzapolis.util.DbUtils;
import com.jhuep.web_development.pizzapolis.entity.Customer;
import com.jhuep.web_development.pizzapolis.entity.OrderPizza;
import com.jhuep.web_development.pizzapolis.entity.OrderSides;
import com.jhuep.web_development.pizzapolis.entity.OrderSummary;
import com.jhuep.web_development.pizzapolis.entity.Pizza;
import com.jhuep.web_development.pizzapolis.entity.PizzaCrust;
import com.jhuep.web_development.pizzapolis.entity.PizzaSauce;
import com.jhuep.web_development.pizzapolis.entity.Sides;
import com.jhuep.web_development.pizzapolis.entity.Toppings;
import com.jhuep.web_development.pizzapolis.obj.PizzaCoverageType;
import com.jhuep.web_development.pizzapolis.obj.PizzaCrustType;
import com.jhuep.web_development.pizzapolis.obj.PizzaSauceType;
import com.jhuep.web_development.pizzapolis.obj.PizzaSizeType;
import com.jhuep.web_development.pizzapolis.obj.ToppingsType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 9, 2017
 */
public class PizzaHelper {

    public static void main(String... args) throws Exception {
        Set<Pizza> pizzas = pizzaGenerator(3);
        List<Sides> sides = sidesGenerator(3);
        Customer c = buildCustomer("jdavidnewcomer@gmail.com", "newcojd1", "David Newcomer", "Password123");
        DbUtils.inTransaction(s -> {
            s.saveOrUpdate(c);
//            sides.forEach(p -> System.out.println(p.getId()));
//            sides.forEach(s::saveOrUpdate);
            pizzas.forEach(p -> System.out.println(p.getId()));
            pizzas.forEach(s::saveOrUpdate);
            OrderSummary order = buildOrder(c, pizzas, sides);
            s.saveOrUpdate(order);
            order.getOrderPizzas().stream()
                    .peek(o -> o.setOrderSummary(order))
                    .forEach(s::saveOrUpdate);
            order.getOrderSideses().stream()
                    .peek(o -> o.setOrderSummary(order))
                    .forEach(s::saveOrUpdate);
        });
        DbUtils.shutdownSessionFactory();
    }

    private static final Random r = new Random(System.currentTimeMillis());

    private static <T extends Enum<T>> T random(Class<T> clazz) {
        T[] values = clazz.getEnumConstants();
        return values[r.nextInt(values.length)];
    }

    private static <T> T random(List<T> list) {
        return list.get(r.nextInt(list.size()));
    }

    private static <T> List<T> randomN(List<T> list, int n) {
        return IntStream.range(0, n)
                .mapToObj(i -> random(list))
                .collect(Collectors.toList());
    }

    private static List<Sides> sidesGenerator(int i) {
        return randomN(RetrieveHelper.retrieveAllSides(), i);
    }

    private static Set<Pizza> pizzaGenerator(int numPizzas) {
        Set<Pizza> result = new HashSet<>();
        while (numPizzas-- > 0) {
            int numToppings = r.nextInt(ToppingsType.values().length);
            List<ToppingsType> toppingsList = new ArrayList<>(Arrays.asList(ToppingsType.values()));
            Collections.shuffle(toppingsList, r);

            Map<ToppingsType, PizzaCoverageType> toppings = new HashMap<>();
            while (numToppings-- > 0) {
                ToppingsType topping = toppingsList.remove(0);
                toppings.put(topping, random(PizzaCoverageType.class));
            }
            result.add(createPizza(random(PizzaSizeType.class), random(PizzaCrustType.class), random(PizzaSauceType.class), toppings));
        }
        return result;
    }

    public static double calcToppingsCost(Pizza pizza, Toppings toppings) {
        System.out.println("toppings = " + toppings);
        PizzaCoverageType coverage = PizzaCoverageType.fromString(toppings.getPizzaCoverage());
        System.out.println("coverage = " + coverage);
        System.out.println("pizza.getSize() = " + pizza.getSize());
        PizzaSizeType pizzaSize = PizzaSizeType.fromString(pizza.getSize());
        System.out.println("pizzaSize = " + pizzaSize);
        return toppings.getPrice() * coverage.getCostModifier() * pizzaSize.getToppingsModifier();
    }

    public static double calcPizzaCost(Pizza pizza) {
        double cost = 0;
        cost += pizza.getPizzaSauce().getCost();
        cost += pizza.getPizzaCrust().getCost();
//        cost += PizzaSize.fromString(pizza.getSize()).getPizzaPrice(); //Mutliple sizes not currently offered on website.
        cost += pizza.getToppingses().stream()
                .mapToDouble(toppings -> calcToppingsCost(pizza, toppings))
                .sum();
        return cost;
    }

    private static Double calcTotalCost(OrderSummary eo) {
        double pizzasCost = eo.getOrderPizzas().stream()
                .mapToDouble(pizza -> pizza.getQuantity() * calcPizzaCost(pizza.getPizza()))
                .sum();
        double sidesCost = eo.getOrderSideses().stream()
                .mapToDouble(side -> side.getQuantity() * side.getSides().getPrice())
                .sum();
        return pizzasCost + sidesCost;
    }

    public static OrderPizza buildPizzaOrder(OrderSummary order, Pizza pizza) {
        OrderPizza orderPizza = new OrderPizza();
        orderPizza.setOrderSummary(order);
        orderPizza.setPizza(pizza);
        orderPizza.setQuantity(1);
        return orderPizza;
    }

    public static OrderSides buildOrderSide(OrderSummary order, Sides side, int quantity) {
        OrderSides result = new OrderSides();
        result.setOrderSummary(order);
        result.setQuantity(quantity);
        result.setSides(side);
        return result;
    }

    public static OrderSummary buildOrder(Customer c, Collection<Pizza> pizzas, List<Sides> sides) {
        OrderSummary eo = new OrderSummary();
        eo.setCustomer(c);
        eo.setDate(new Date());
        Set<OrderPizza> pizzaOrders = pizzas.stream().map(p -> buildPizzaOrder(eo, p)).collect(Collectors.toSet());
        Map<Sides, List<Sides>> collect = sides.stream().collect(Collectors.groupingBy(Function.identity()));
        Set<OrderSides> sideOrders = collect.entrySet().stream().map(p -> buildOrderSide(eo, p.getKey(), p.getValue().size())).collect(Collectors.toSet());
        eo.setOrderSideses(sideOrders);
        eo.setTotalCost(calcTotalCost(eo));
        return new OrderSummary(c, new Date(), calcTotalCost(eo), sideOrders, pizzaOrders);
    }

    public static Customer buildCustomer(String email, String login, String username, String password) {
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setLogin(login);
        customer.setName(username);
        customer.setPassword(password);
        return customer;
    }

    public static Pizza createPizza(PizzaSizeType size, PizzaCrust crustType, PizzaSauce sauceType, Set<Toppings> toppings) {
        return new Pizza(crustType, sauceType, size.toString(), toppings, new HashSet<>());
    }

    public static Pizza createPizza(PizzaSizeType size, PizzaCrustType crustType, PizzaSauceType sauceType, Map<ToppingsType, PizzaCoverageType> toppings) {
        PizzaCrust crust = RetrieveHelper.getCrust(crustType);
        PizzaSauce sauce = RetrieveHelper.getSauce(sauceType);
        Set<Toppings> myToppings = toppings.entrySet().stream()
                .map(e -> RetrieveHelper.getTopping(e.getKey(), e.getValue()))
                .collect(Collectors.toSet());
        return createPizza(size, crust, sauce, myToppings);
    }
}
