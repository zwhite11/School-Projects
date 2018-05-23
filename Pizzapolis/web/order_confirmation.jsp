<%-- 
    Document   : order_confirmation
    Created on : May 9, 2017, 10:17:36 PM
    Author     : David Newcomer<david.newcomer@jhuapl.edu>
--%>

<%@page import="com.jhuep.web_development.pizzapolis.database.RetrieveHelper"%>
<%@page import="com.jhuep.web_development.pizzapolis.entity.*" %>
<%@page import="java.io.*,java.util.*" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <title>Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="style.css">
    <style>
        body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif}
        .w3-bar,h1,button {font-family: "Montserrat", sans-serif}
        .fa-anchor,.fa-coffee {font-size:200px}
    </style>
    <body>

        <!-- Navbar -->
        <div class="w3-top">
            <div class="w3-bar w3-red w3-card-2 w3-left-align w3-large">
                <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
                <a href="./index.html" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
                <a href="./aboutus.html" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">About Pizzapolis</a>
                <div class="w3-dropdown-hover w3-red">
                    <button class="w3-button w3-hide-small w3-padding-large w3-red w3-hover-white">Menu</button>
                    <div class="w3-red w3-dropdown-content w3-bar-block w3-card-4 ">
                        <a href="./pizza.html" class="w3-bar-item w3-button">Pizza</a>
                        <a href="./sides.html" class="w3-bar-item w3-button">Sides</a>

                    </div>
                </div>  
                <a href="./contactus.html" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Contact Us</a>
                <a href="./order.html" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Order Online Now!</a>
                <a href="./viewCart.html" class="w3-bar-item w3-button w3-padding-large">Your Cart</a>
            </div>
        </div>

        <!--TOdo: Replace the below with a call to the database.
        We need to insert all of the relevant data into the database
        then send an E-Mail confirmation.
        -->
        <!--         First Grid 
                <div class="w3-row-padding w3-padding-64 w3-container">
                    <div class="w3-content">
                        <div class="w3-twothird">
                            <h1>Order</h1>
        
                            <p class="w3-xlarge">What would you like to order?</p>
                            <form action="./orderPizza.html">
                                <button class="w3-button w3-black w3-padding-large w3-large w3-margin-top" type="submit">Pizza</button>
                            </form>
                            <form action="./orderSide.html">
                                <button class="w3-button w3-black w3-padding-large w3-large w3-margin-top" type="submit">Sides</button>
                            </form>
                        </div>
        
                    </div>
        
                </div>-->

    </body>
</html>
