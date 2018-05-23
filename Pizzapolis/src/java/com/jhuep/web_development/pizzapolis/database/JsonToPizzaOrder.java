package com.jhuep.web_development.pizzapolis.database;

import com.jhuep.web_development.pizzapolis.util.JsonHelper;
import com.jhuep.web_development.pizzapolis.entity.Customer;
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
import com.jhuep.web_development.pizzapolis.obj.SidesSizeType;
import com.jhuep.web_development.pizzapolis.obj.SidesType;
import com.jhuep.web_development.pizzapolis.obj.ToppingsType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 10, 2017
 */
public class JsonToPizzaOrder {

//    public static void main(String... args) throws Exception {
//        String json = "{\"pizzas\":[{\"deleteKey\":\"Pizza1\",\"crust\":{\"type\":\"Thin\",\"price\":5},\"sauce\":\"Tomato\",\"toppings\":[{\"leftToppings\":[\"Green Peppers\"]},{\"wholeToppings\":[\"Ground Beef\"]},{\"rightToppings\":[]}],\"cost\":13},{\"deleteKey\":\"Pizza2\",\"crust\":{\"type\":\"Whole Grain\",\"price\":8},\"sauce\":\"BBQ\",\"toppings\":[{\"leftToppings\":[\"Sausage\",\"Mushrooms\",\"Pineapple\"]},{\"wholeToppings\":[\"Pepperoni\",\"Chicken\",\"Bacon\",\"Tomatoes\"]},{\"rightToppings\":[\"Ham\",\"Banana Peppers\",\"Spinach\"]}],\"cost\":28}],\"sides\":[{\"deleteKey\":\"Side1\",\"name\":\"Garlic Bread\",\"size\":\"Small\",\"cost\":3},{\"deleteKey\":\"Side2\",\"name\":\"Cheese Bread\",\"size\":\"Medium\",\"cost\":5},{\"deleteKey\":\"Side3\",\"name\":\"Bread Sticks\",\"size\":\"Large\",\"cost\":7},{\"deleteKey\":\"Side4\",\"name\":\"Bread Sticks\",\"size\":\"Large\",\"cost\":7},{\"deleteKey\":\"Side5\",\"name\":\"Bread Sticks\",\"size\":\"Medium\",\"cost\":5}],\"total\":68}";
//        Customer c = RetrieveHelper.retrieveAll(Customer.class).iterator().next();
//        persistOrder(c, json);
//        DbUtils.shutdownSessionFactory();
//    }
    public static OrderSummary persistOrder(Customer c, String json) {
        List<Pizza> pizzas = buildPizzas(json);
        List<Sides> sides = buildSides(json);
        return PerstanceHelper.persistOrder(c, pizzas, sides);
    }

    public static Sides buildSide(JsonHelper json) {
        SidesSizeType size = SidesSizeType.fromString(json.findSingle(k -> k.equals("size")).getValue().toString());
        SidesType type = SidesType.fromString(json.findSingle(k -> k.equalsIgnoreCase("name")).getValue().toString());
        return RetrieveHelper.getSide(type, size);
    }

    public static List<Sides> buildSides(String json) {
        JsonHelper helper = new JsonHelper(json);
        return helper.find(f -> f.startsWith("sides["))
                .stream()
                .map(JsonToPizzaOrder::buildSide)
                .collect(Collectors.toList());
    }

    public static List<Pizza> buildPizzas(String json) {
        JsonHelper helper = new JsonHelper(json);
        List<JsonHelper> pizzas = helper.find(key -> key.toLowerCase().contains("pizzas["));
        return pizzas.stream().map(JsonToPizzaOrder::buildPizza).collect(Collectors.toList());
    }

    private static Set<Toppings> buildToppings(JsonHelper json) {
        Set<Toppings> result = new HashSet<>();
        json.find(key -> key.startsWith("leftToppings[")).stream()
                .map(t -> RetrieveHelper.getTopping(ToppingsType.fromString("" + t.getValue()), PizzaCoverageType.LEFT))
                .forEachOrdered(result::add);
        json.find(key -> key.startsWith("wholeToppings[")).stream()
                .map(t -> RetrieveHelper.getTopping(ToppingsType.fromString("" + t.getValue()), PizzaCoverageType.WHOLE))
                .forEachOrdered(result::add);
        json.find(key -> key.startsWith("rightToppings[")).stream()
                .map(t -> RetrieveHelper.getTopping(ToppingsType.fromString("" + t.getValue()), PizzaCoverageType.RIGHT))
                .forEachOrdered(result::add);
        return result;
    }

    private static PizzaCrust extractCrust(JsonHelper json) {
        String value = json.findSingle(key -> key.toLowerCase().contains("crust"))
                .findSingle(key -> key.startsWith("type")).getValue().toString();
        return RetrieveHelper.getCrust(PizzaCrustType.fromString(value));
    }

    private static PizzaSizeType extractSize(JsonHelper json) {
        return PizzaSizeType.MEDIUM; //This should be added as an option to the form, but for now, we have just one size.
    }

    public static Pizza buildPizza(JsonHelper json) {
        Set<Toppings> toppings = buildToppings(json.findSingle(key -> key.equalsIgnoreCase("toppings")));
        PizzaCrust crust = extractCrust(json);
        PizzaSizeType size = extractSize(json);
        PizzaSauce sauce = extractSauce(json);
        return PizzaHelper.createPizza(size, crust, sauce, toppings);
    }

    private static PizzaSauce extractSauce(JsonHelper json) {
        String sauceName = json.findSingle(key -> key.equals("sauce")).getValue().toString();
        return RetrieveHelper.getSauce(PizzaSauceType.fromString(sauceName));
    }

}
