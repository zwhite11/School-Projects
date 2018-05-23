<%@page import="com.jhuep.web_development.pizzapolis.database.RetrieveHelper"%>
<%@page import="com.jhuep.web_development.pizzapolis.entity.*" %>
<%@page import="java.io.*,java.util.*" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <title>Pizzapolis</title>
    <script src="clearGroup.js"></script>
    <script src="checkoutValidate.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="w3.css">
    <link rel="stylesheet" href="checkoutCSS.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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

        <div class="w3-row-padding w3-padding-64 w3-container">
            <div class="w3-content">
                <div class="w3-half">
                    <div class="form-style-2">

                        <p id="total" style='font-size: 20px;'></p>
                        <script>
                            var existingCart = localStorage.getItem("cart");
                            var cartJson = JSON.parse(existingCart);

                            var totalText = "Total: $";
                            totalText += cartJson.total;

                            document.getElementById("total").innerHTML = totalText;

                        </script>
                        <p id="errors"></p>
                        <div class="form-style-2-heading">Provide your information</div>

                        <%
                            Customer customer = (Customer) session.getAttribute("customer");
                            if (customer == null) {
                                customer = new Customer("", "", "", "", "", "", new HashSet<OrderSummary>());
                            }
                        %>
                        <form name="checkout" action="Pizzapolis" method="post">
                            <label for="field1"><span>Name <span class="required">*</span></span><input type="text" class="input-field" name="field1" value="<%=customer.getName()%>" required/></label>
                            <label for="field2"><span>Email <span class="required">*</span></span><input type="text" class="input-field" name="field2" value="<%=customer.getEmail()%>"  required/></label>
                            <label><span>Telephone</span><input type="text" class="tel-number-field" name="tel_no" value="<%=customer.getPhoneNumber()%>"  /></label>
                            <label for="field4"><span>Card Type</span><select name="field4" class="select-field">
                                    <option value="Visa">Visa</option>
                                    <option value="MaterCard">MasterCard</option>
                                    <option value="Amex">American Express</option>
                                </select></label>
                            <label for="field5"><span>Credit Card Number <span class="required">*</span></span><input type="text" class="input-field" name="field5" maxlength="16"  required/></label>
                            <label for="field6"><span>Security Code<span class="required">*</span></span><input type="text" class="input-field" name="field6" maxlength="4" required/></label>

                            <label for="field7"><span>Credit Card Expiration:</span>
                                <select name="field7" class="select-field">

                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                    <option>4</option>
                                    <option>5</option>
                                    <option>6</option>
                                    <option>7</option>
                                    <option>8</option>
                                    <option>9</option>
                                    <option>10</option>
                                    <option>11</option>
                                    <option>12</option>
                                </select>

                                <select name="field7" class="select-field">
                                    <option>2017</option>
                                    <option>2018</option>
                                    <option>2019</option>
                                    <option>2020</option>
                                    <option>2021</option>
                                    <option>2022</option>
                                </select>
                            </label>

                            <input id="showCart" type="hidden" name="cart_json" readonly><br>
                            <script>
                                document.getElementById("showCart").value = localStorage.getItem("cart");
                            </script>


                            <button class="w3-button w3-black w3-padding-large w3-round-xxlarge w3-margin-top" type="submit" onclick="return validateForm();">Submit</button>
                        </form>


                    </div>
                </div>
            </div>
        </div>
    </body>
</html>