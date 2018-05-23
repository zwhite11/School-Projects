package com.jhuep.web_development.pizzapolis;

import com.jhuep.web_development.pizzapolis.database.PerstanceHelper;
import com.jhuep.web_development.pizzapolis.database.RetrieveHelper;
import com.jhuep.web_development.pizzapolis.entity.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 */
public class Checkout extends HttpServlet {

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
        String button = request.getParameter("button");
        try (PrintWriter out = response.getWriter()) {
            out.println(ServletHelper.HEADER);
            if (request.getAttribute("customer") == null) {
                dispatchLoginPage(request, response);
            } else if (button.equalsIgnoreCase("do_checkout")) {
                //If we succeed in registering a new user, then login as that user and move to checkout.
                handleRegisterAttempt(request, response, out);
            } else if (button.equalsIgnoreCase("do_login")) {
                //Attempting to log in with existing credentials
                handleLoginAttempt(request, response, out);
            }
//            out.println("<h1>Order Confirmation</h1>\n");
//            out.println("<p Your order has succesfully ben placed. </p>");
//            out.println("<p class=\"w3-xlarge\">What would you like to order?</p>\n");
//            out.println("<form action=\"./orderPizza.html\">\n");
//            out.println("    <button class=\"w3-button w3-black w3-padding-large w3-large w3-margin-top\" type=\"submit\">Pizza</button>\n");
//            out.println("</form>\n");
//            out.println("<form action=\"./orderSide.html\">\n");
//            out.println("    <button class=\"w3-button w3-black w3-padding-large w3-large w3-margin-top\" type=\"submit\">Sides</button>\n");
//            out.println("</form>\n");
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

    private void dispatchLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.html");
        dispatcher.forward(request, response);
    }

    private void handleLoginAttempt(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException, ServletException {
        Customer customer = RetrieveHelper.retrieveCustomer(request.getParameter("username"), request.getParameter("password"));
        if (customer == null) {
            out.println("Unrecognized username or password.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.html");
            dispatcher.forward(request, response);
        } else {
            out.println("Thank you for logging in " + customer.getName());
            request.getSession().setAttribute("customer", customer);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/checkout.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void handleRegisterAttempt(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String message = "";
        if (RetrieveHelper.doesCustomerLoginExist(username)) {
            //Customer already exists, throw up a warning
            out.println("I'm sorry, we already have a customer with the username " + username + ". Please select a different username");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/register.html");
            dispatcher.forward(request, response);
        } else {
            Customer c = new Customer(name, email, username, password, address, phone, new HashSet<>());
            Customer persistantCustomer = PerstanceHelper.createNewCustomer(c);
            request.setAttribute("customer", persistantCustomer);
            out.println("Thank you for registering with us, " + name + "! We definitely won't share any of this information with China *wink*");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/checkout.jsp");
            dispatcher.forward(request, response);
        }

    }

}
