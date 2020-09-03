$(function () {

    $('#submit').on('click', function (event) {
        var password = $("#password").val();
        var confirm_password = $("#confirm_password").val();

        if (password !== confirm_password && confirm_password !== undefined) {
            showErrorMessage("Password and confirm password should be the same!", event);
        } else if (!validatePassword()) {
            showErrorMessage("Password should be alphanumeric and more than 4 characters!", event);
        }

        if (!validateName())
            showErrorMessage("Name should be alphabetic and more than 3 characters!", event);

        if (!validateFamily())
            showErrorMessage("Family should be alphabetic and more than 3 characters!", event);

        if (!validateEmailAddress())
            showErrorMessage("It seems you entered an invalid email, Please check it again.", event);
    });

});

function validatePassword() {
    var password = $("#password").val();
    return !(!password.match(/^[a-z0-9]+$/) || password.length < 8 || password.length > 20);
}

function validateName() {
    var name = $("#name").val();
    return !(!name.match(/^[A-Za-z]+$/) || name.length < 3 || name.length > 20);
}

function validateFamily() {
    var family = $("#family").val();
    return !(!family.match(/^[A-Za-z]+$/) || family.length < 3 || family.length > 30);
}

function validateEmailAddress() {
    var email = $("#email").val();
    return !(!email.match(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/));
}