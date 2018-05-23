/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function printCart() {
    var thisCart = localStorage.getItem("cart");
    var cartJson = JSON.parse(thisCart);

    var text = "";
    var i = 0;
    var l, w, r;

    var total = 0.0;

    if (thisCart === null) {
        return "<h1 class='w3-twothird'> Your Cart is Empty</h1>";
    }

    else {
        var total = 0.0;

        //print pizzas
        for (var i = 0; i < cartJson.pizzas.length; i++) {
            var deleteKey = "Pizza" + (i + 1);
            text += "<h2> Pizza " + (i + 1) + "</h2>";
            var pizza = cartJson.pizzas[i];
            pizza.deleteKey = deleteKey;

            var crust = pizza.crust;
            switch (crust) {
                case "Thin":

            }

            text += "<p style='font-size: 20px;'> Crust: " + pizza.crust.type + "</p>";
            text += "<p style='font-size: 20px;'> Sauce: " + pizza.sauce + "</p>";

            if (pizza.toppings[0].leftToppings.length > 0) {
                text += "<p style='font-size: 20px;'> Left Toppings: </p> <ul>";
                for (l in pizza.toppings[0].leftToppings) {
                    text += "<li>" + pizza.toppings[0].leftToppings[l] + "</li>";
                }
                text += "</ul>";
            }

            if (pizza.toppings[1].wholeToppings.length > 0) {
                text += "<p style='font-size: 20px;'> Whole Toppings: </p> <ul>";
                for (w in pizza.toppings[1].wholeToppings) {
                    text += "<li>" + pizza.toppings[1].wholeToppings[w] + "</li>";
                }
                text += "</ul>";
            }

            if (pizza.toppings[2].rightToppings.length > 0) {
                text += "<p style='font-size: 20px;'> Right Toppings: </p> <ul>";
                for (r in pizza.toppings[2].rightToppings) {
                    text += "<li>" + pizza.toppings[2].rightToppings[r] + "</li>";
                }
            }

            text += "</ul>";
            var pizzaPrice = "<p style='font-size: 28px;'>Pizza Price: $" + pizza.cost + "</p>";


            text += pizzaPrice;

            //add button for removing pizza
            var removePizzaButton = '';
            removePizzaButton += '<form action="""" method="post">' +
                    '<button class="w3-button w3-red w3-padding-large w3-round-xxlarge w3-margin-top" type="submit" onClick="removePizzaFromCart(\'' + pizza.deleteKey + '\');">Remove Pizza' +
                    '</button></form> ';

            text += removePizzaButton + "<hr>";

            total += pizza.cost;
        }

        //print sides
        for (var i = 0; i < cartJson.sides.length; i++) {
            var deleteKey = "Side" + (i + 1);
            text += "<h3> Side " + (i + 1) + "</h3>";
            var side = cartJson.sides[i];
            side.deleteKey = deleteKey;

            var sidePrice = "<p style='font-size: 28px;'>Side Price: $" + side.cost + "</p>";


            //add button for removing sides
            var removeSideButton = '';
            removeSideButton += '<form action="""" method="post">' +
                    '<button class="w3-button w3-red w3-padding-large w3-round-xxlarge w3-margin-top" type="submit" onClick="removeSideFromCart(\'' + side.deleteKey + '\');">Remove Side' +
                    '</button></form> ';

            text += side.size + " " + side.name + sidePrice + removeSideButton + "<hr>";

            total += side.cost;
        }

        var checkOutButton = '';
        checkOutButton += '<form action="checkout.jsp" method="post">' +
                '<button class="w3-button w3-black w3-padding-large w3-round-xxlarge w3-margin-top" type="submit">Checkout' +
                '</button></form> ';

        text += checkOutButton;



    }
    cartJson.total = total;
    text += "<p style='font-size: 28px;'>Total: $" + cartJson.total + "</p>";

    var cartStringify = JSON.stringify(cartJson);
    localStorage.setItem("cart", cartStringify);
    return text;
}


function removePizzaFromCart(toDelete) {

    var thisCart = localStorage.getItem("cart");
    var cartJson = JSON.parse(thisCart);

    if (cartJson.pizzas.length === 1) {
        cartJson.pizzas = [];
    }
    else {
        for (var i = 0; i < cartJson.pizzas.length; i++) {
            if (cartJson.pizzas[i].deleteKey === toDelete) {
                cartJson.pizzas.splice(i, 1);
            }
        }
    }

    var cartStringify = JSON.stringify(cartJson);
    localStorage.setItem("cart", cartStringify);

}

function removeSideFromCart(toDelete) {
    var thisCart = localStorage.getItem("cart");
    var cartJson = JSON.parse(thisCart);

    if (cartJson.sides.length === 1) {
        cartJson.sides = [];
    }
    else {
        for (var i = 0; i < cartJson.sides.length; i++) {
            if (cartJson.sides[i].deleteKey === toDelete) {
                cartJson.sides.splice(i, 1);
            }
        }
    }

    var cartStringify = JSON.stringify(cartJson);
    localStorage.setItem("cart", cartStringify);
}

function emptyCart() {
    localStorage.removeItem("cart");
}

