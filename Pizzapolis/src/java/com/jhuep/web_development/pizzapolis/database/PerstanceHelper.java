package com.jhuep.web_development.pizzapolis.database;

import com.jhuep.web_development.pizzapolis.util.DbUtils;
import static com.jhuep.web_development.pizzapolis.database.PizzaHelper.buildOrder;
import com.jhuep.web_development.pizzapolis.entity.Customer;
import com.jhuep.web_development.pizzapolis.entity.OrderSummary;
import com.jhuep.web_development.pizzapolis.entity.Pizza;
import com.jhuep.web_development.pizzapolis.entity.Sides;
import java.util.List;
import java.util.Set;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 9, 2017
 */
public class PerstanceHelper {

    public static Customer createNewCustomer(Customer transientCustomer) {
        DbUtils.inTransaction(s -> s.saveOrUpdate(transientCustomer));
        return transientCustomer;
    }

    public static List<Customer> getAllCustomers() {
        return RetrieveHelper.retrieveAll(Customer.class);
    }

    public static OrderSummary persistOrder(Customer c, List<Pizza> pizzas, List<Sides> sides) {
        OrderSummary order = buildOrder(c, pizzas, sides);
        DbUtils.inTransaction(s -> {
//            s.saveOrUpdate(c); //Assume customer is already persisted
            pizzas.forEach(s::saveOrUpdate);
            s.saveOrUpdate(order);
            order.getOrderPizzas().stream()
                    .peek(o -> o.setOrderSummary(order))
                    .forEach(s::saveOrUpdate);
            order.getOrderSideses().stream()
                    .peek(o -> o.setOrderSummary(order))
                    .forEach(s::saveOrUpdate);
        });
        return order;
    }

}
