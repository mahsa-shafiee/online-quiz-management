$(document).ready(function () {

    $.getScript('/js/dataTable.js', function () {

        $('#search-user').on("click", "#search", (function (event) {
            updateTable(event, page, true);
        }));

        $('#usersTable').on("click", "#confirm", (function (event) {
            var id = $(this).parent().find('#confirm').attr('value');
            confirmUser(event, page, id);
        }));

    });

});

function confirmUser(event, page, id) {
    $.ajax({
        type: 'get',
        async: false,
        cache: false,
        url: window.location + "/confirm/" + id,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            var alert = confirm("Are you sure about confirming this user?");
            if (!alert)
                return false;
        },
        error: function () {
            alert("This user already confirmed.")
        }
    })
    updateTable(event, page);
}

function updateTable(event, page, isSubmit) {
    var name = $('#name').val();
    var family = $('#family').val();
    var emailAddress = $('#emailAddress').val();
    var password = $('#password').val();
    var role = $('#role').val();
    var registrationStatus = $('#registrationStatus').val();

    $.ajax({
        type: 'get',
        url: window.location + "/search/" + page + "?name=" + name + "&&family=" + family
            + "&&emailAddress=" + emailAddress + "&&password=" + password
            + "&&role=" + role + "&&registrationStatus=" + registrationStatus,
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false,
    }).done(function (data) {
        fillTable(data);
        if (isSubmit === true)
            event.preventDefault();
    });
}

function fillTable(data) {
    $('#usersTable tfoot').empty();

    $.each(data, function (index, element) {
        $('#usersTable tfoot').append(
            fillUserInformation(element) +
            '<td>' +
            '<a style="cursor: pointer" id="confirm" value="' + element.id + '">' + 'confirm' + '</a>' +
            '</td>' +
            '<td>' +
            '<a id="update" href="' + "/users/update/" + element.id + '">' + 'update' + '</a>' +
            '</td></tr>'
        );
    });
}