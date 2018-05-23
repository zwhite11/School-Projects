package com.jhuep.web_development.pizzapolis;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 10, 2017
 */
public class ServletHelper {

    public static final String navbar = "<!DOCTYPE html>\n"
            + "<html>\n"
            + "    <title>Pizzapolis</title>\n"
            + "    <script src=\"clearGroup.js\"></script>\n"
            + "    <script src=\"checkoutValidate.js\"></script>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
            + "    <link rel=\"stylesheet\" href=\"w3.css\">\n"
            + "    <link rel=\"stylesheet\" href=\"checkoutCSS.css\">\n"
            + "    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Lato\">\n"
            + "    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Montserrat\">\n"
            + "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n"
            + "    <style>\n"
            + "        body,h1,h2,h3,h4,h5,h6 {font-family: \"Lato\", sans-serif}\n"
            + "        .w3-bar,h1,button {font-family: \"Montserrat\", sans-serif}\n"
            + "        .fa-anchor,.fa-coffee {font-size:200px}\n"
            + "    </style>\n"
            + "    <body>\n"
            + "\n"
            + "        <!-- Navbar -->\n"
            + "        <div class=\"w3-top\">\n"
            + "            <div class=\"w3-bar w3-red w3-card-2 w3-left-align w3-large\">\n"
            + "                <a class=\"w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red\" href=\"javascript:void(0);\" onclick=\"myFunction()\" title=\"Toggle Navigation Menu\"><i class=\"fa fa-bars\"></i></a>\n"
            + "                <a href=\"./index.html\" class=\"w3-bar-item w3-button w3-padding-large w3-white\">Home</a>\n"
            + "                <a href=\"./aboutus.html\" class=\"w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white\">About Pizzapolis</a>\n"
            + "                <div class=\"w3-dropdown-hover w3-red\">\n"
            + "                    <button class=\"w3-button w3-hide-small w3-padding-large w3-red w3-hover-white\">Menu</button>\n"
            + "                    <div class=\"w3-red w3-dropdown-content w3-bar-block w3-card-4 \">\n"
            + "                        <a href=\"./pizza.html\" class=\"w3-bar-item w3-button\">Pizza</a>\n"
            + "                        <a href=\"./sides.html\" class=\"w3-bar-item w3-button\">Sides</a>\n"
            + "\n"
            + "                    </div>\n"
            + "                </div>  \n"
            + "                <a href=\"./contactus.html\" class=\"w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white\">Contact Us</a>\n"
            + "                <a href=\"./order.html\" class=\"w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white\">Order Online Now!</a>\n"
            + "                <a href=\"./viewCart.html\" class=\"w3-bar-item w3-button w3-padding-large\">Your Cart</a>\n"
            + "            </div>\n"
            + "        </div>";

    public static final String HEADER = navbar + "\n"
            + "        <!-- First Grid -->\n"
            + "        <div class=\"w3-row-padding w3-padding-64 w3-container\">\n"
            + "            <div class=\"w3-content\">\n"
            + "                <div class=\"w3-twothird\">\n";

    public static final String FOOTER
            = "                </div>\n"
            + "            </div>\n"
            + "        </div>\n"
            + "    </body>\n"
            + "</html>";
}
