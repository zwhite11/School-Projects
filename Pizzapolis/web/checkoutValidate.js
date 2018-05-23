

function validateForm() {
    var errors = "";

    //check name
    var n = document.forms["checkout"]["field1"].value;
    if (n === "" || n === null) {
        errors += "You must enter your name <br>";
    }
    //check email    
    var x = document.forms["checkout"]["field2"].value;
    var atpos = x.indexOf("@");
    var dotpos = x.lastIndexOf(".");
    if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= x.length) {
        errors += "Email Address is invalid <br>";
    }

    //check phone number
    var t1 = document.forms["checkout"]["tel_no_1"].value;
    var t2 = document.forms["checkout"]["tel_no_2"].value;
    var t3 = document.forms["checkout"]["tel_no_3"].value;
    if (isNaN(t1) || isNaN(t2) || isNaN(t3)) {
        errors += "Telephone number is invalid <br>";
    }

    //check card number
    var cardNum = document.forms["checkout"]["field5"].value;
    if (isNaN(cardNum) || cardNum.length < 16) {
        errors += "Credit card number is invalid <br>";
    }

    //check card code
    var cardCode = document.forms["checkout"]["field6"].value;
    var cardType = document.forms["checkout"]["field5"].value;
    //amex has 4 digit codes
    var wrongCode = false;
    //amex chosen, but not a 4 digit security code
    if (cardType === "Amex" && cardCode.length < 4) {
        wrongCode = true;
    }
    //not amex chosen, but 4 digit code entered.
    if (cardType !== "Amex" && cardCode.length > 3) {
        wrongCode = true;
    }
    if (isNaN(cardCode) || wrongCode === true) {
        errors += "Security Code invlid <br>";
    }

    //there are errors
    if (errors.length > 1) {
        document.getElementById("errors").innerHTML = errors;
        return false;
    }
    //no errors
    else {
        //document.getElementById("errors").innerHTML = "ALL GOOD";
        localStorage.removeItem("cart");
        return true;
    }
}