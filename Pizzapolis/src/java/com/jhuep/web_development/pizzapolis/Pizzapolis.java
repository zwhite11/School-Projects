package com.jhuep.web_development.pizzapolis;

import com.jhuep.web_development.pizzapolis.database.JsonToPizzaOrder;
import com.jhuep.web_development.pizzapolis.database.RetrieveHelper;
import com.jhuep.web_development.pizzapolis.entity.Customer;
import com.jhuep.web_development.pizzapolis.entity.OrderPizza;
import com.jhuep.web_development.pizzapolis.entity.OrderSides;
import com.jhuep.web_development.pizzapolis.entity.OrderSummary;
import com.jhuep.web_development.pizzapolis.entity.Pizza;
import com.jhuep.web_development.pizzapolis.entity.Toppings;
import com.jhuep.web_development.pizzapolis.obj.PizzaCoverageType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 */
public class Pizzapolis extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Customer c = (Customer) request.getAttribute("customer");
        if (c == null) {
            //TODO:  We either need to:
            //1. Force the user to login before they are allowed to checkout
            // or
            //2. Create a Customer from the Checkout Form contents.
            c = RetrieveHelper.retrieveAll(Customer.class).iterator().next(); //Note: This is the WRONG customer, but should work as a placeholder.
        }
        OrderSummary orderSummary = JsonToPizzaOrder.persistOrder(c, request.getParameter("cart_json"));

        MailUtilGmail email = new MailUtilGmail();
        String to = c.getEmail();
        String from = "webapptest34@gmail.com";
        String subject = "Pizzapolis Order Confirmation";
        String receipt = "\n Your order is below. \n\n";

        Set<OrderPizza> pizzas = orderSummary.getOrderPizzas();
        if (!pizzas.isEmpty()) {
            receipt += "Pizzas: \n";
            for (OrderPizza op : pizzas) {
                Pizza p = op.getPizza();
                receipt += p.getId() + "\n";
                receipt += p.getPizzaCrust()
                        + p.getPizzaCrust().getCrustName() + "\n";
                receipt += p.getPizzaSauce().getSauceName() + "\n";
                Set<Toppings> toppings = p.getToppingses();
                String left = "Left Toppings: \n";
                String whole = "Whole Toppings: \n";
                String right = "Right Toppings: \n";
                String allToppings = toppings.stream()
                        .collect(Collectors.groupingBy(t -> PizzaCoverageType.fromString(t.getPizzaCoverage())))
                        .entrySet().stream()
                        .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
                        .map(e -> e.getKey().join(e.getValue()))
                        .collect(Collectors.joining("\n"));
                receipt += allToppings + "\n";
            }
        }

        Set<OrderSides> sides = orderSummary.getOrderSideses();
        if (!sides.isEmpty()) {
            receipt += "Sides: \n";
            for (OrderSides os : sides) {
                receipt += os.getSides().getSize() + " " + os.getSides().getName() + "\n";
            }
        }

        String body = "Thank you for your order " + c.getName()
                + ". Your total cost is $" + orderSummary.getTotalCost() + receipt;

        new Thread() {

            @Override
            public void run() {
                try {
                    email.sendMail(to, from, subject, body, false);
                } catch (MessagingException ex) {
                    Logger.getLogger(Pizzapolis.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }.start();

        try (PrintWriter out = response.getWriter()) {
            out.println(ServletHelper.HEADER);
            out.println("<h1>Order Confirmation</h1>\n");
            out.println("<p> Your order has succesfully ben placed. </p>");
            out.println("<p>" + receipt + "</p>");

            out.println(ServletHelper.FOOTER);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
