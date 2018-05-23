/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getRadioVal(form, name) {
    var value;

    var radios = form.elements[name];

    for (var i = 0; i < radios.length; i++) {
        if (radios[i].checked) {
            value = radios[i].value;
            break;
        }
    }
    return value;
}
function addPizzaToCart() {
    var meats = document.getElementById("meats");
    var nonmeats = document.getElementById("nonmeats");

    var i;
    var leftToppings = [];
    var wholeToppings = [];
    var rightToppings = [];

    var selectedMeats = 0;
    //adding meats
    for (i = 0; i < meats.length; i++) {
        var radioButton = meats.elements[i];
        if (radioButton.checked === true) {
            selectedMeats += 1;
            if (radioButton.value === "left") {
                leftToppings.push(radioButton.name);
            }
            if (radioButton.value === "whole") {
                wholeToppings.push(radioButton.name);
            }
            if (radioButton.value === "right") {
                rightToppings.push(radioButton.name);
            }
        }
    }

    var selectedNonmeats = 0;
    //adding non meats
    for (i = 0; i < nonmeats.length; i++) {
        var radioButton = nonmeats.elements[i];
        if (radioButton.checked === true) {
            selectedNonmeats += 1;
            if (radioButton.value === "left") {
                leftToppings.push(radioButton.name);
            }
            if (radioButton.value === "whole") {
                wholeToppings.push(radioButton.name);
            }
            if (radioButton.value === "right") {
                rightToppings.push(radioButton.name);
            }
        }
    }

    var crustList = document.getElementById("crust");
    var crustType = getRadioVal(crustList, "crust");
    var crustPrice = 0.0;
    switch (crustType) {
        case "Thin":
            crustPrice = 5.0;
            break;
        case "Hand Tossed":
            crustPrice = 5.0;
            break;
        case "Cheese Crust":
            crustPrice = 7.0;
            break;
        case "Gluten Free":
            crustPrice = 8.0;
            break;
        case "Whole Grain":
            crustPrice = 8.0;
            break;
    }
    var crust = {
        "type": crustType,
        "price": crustPrice
    };

    var sauceList = document.getElementById("sauce");
    var sauce = getRadioVal(sauceList, "sauce");

    //price for just the sauce
    var pizzaCost = 5.0;
    pizzaCost += crustPrice;
    pizzaCost += (selectedMeats * 2.0);
    pizzaCost += (selectedNonmeats);

    var pizza = {
        "deleteKey": "",
        "crust": crust,
        "sauce": sauce,
        "toppings": [
            {"leftToppings": leftToppings},
            {"wholeToppings": wholeToppings},
            {"rightToppings": rightToppings}
        ],
        "cost": pizzaCost
    };

    var cart = localStorage.getItem("cart");
    var cartJSON = JSON.parse(cart);
    cartJSON.pizzas.push(pizza);




    var cartString = JSON.stringify(cartJSON);
    localStorage.setItem("cart", cartString);
}

function addSideToCart() {
    var sidesForm = document.getElementById("sides");
    var i;
    var cart = localStorage.getItem("cart");
    var cartJSON = JSON.parse(cart);


    for (i = 0; i < sidesForm.length; i++) {
        var radioButton = sidesForm.elements[i];
        if (radioButton.checked === true) {
            var sideName = radioButton.name;
            var size = radioButton.value;
            var sideCost = 0.0;
            switch (size) {
                case "Small":
                    sideCost = 3.0;
                    break;
                case "Medium":
                    sideCost = 5.0;
                    break;
                case "Large":
                    sideCost = 7.0;
                    break;
            }
            var side = {
                "deleteKey": "",
                "name": sideName,
                "size": size,
                "cost": sideCost
            };

            cartJSON.sides.push(side);
        }
    }
    var cartString = JSON.stringify(cartJSON);
    localStorage.setItem("cart", cartString);

}