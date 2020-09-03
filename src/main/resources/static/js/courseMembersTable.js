var courseId = window.location.toString().split("/")[5];

$(document).ready(function () {

    $.getScript('/js/dataTable.js', function () {

        $("#add-member").submit(function (event) {
            var userId = $('#userId').val();
            addNewMember(event, page, userId);
        });

        $('#membersTable').on("click", "#delete", (function (event) {
            var userId = $(this).parent().find('#delete').attr('value');
            deleteMember(event, page, userId);
        }));

    });

});

function addNewMember(event, page, userId) {
    $.ajax({
        type: 'get',
        url: "http://localhost:8080/courses/members/" + courseId + "/add?userId=" + userId,
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false,
        success: function () {
            showMessage("The user added.");
            updateTable(event, page);
            event.preventDefault();
        },
        error: function (xhr) {
            showMessage(xhr.responseText);
            updateTable(event, page);
            event.preventDefault();
        }
    })
}

function showMessage(message) {
    document.getElementById("msg").innerHTML = message;
    $('#msg').css("padding", "0");
}

function deleteMember(event, page, userId) {
    $.ajax({
        type: 'delete',
        url: "http://localhost:8080/courses/members/" + courseId + "/delete?userId=" + userId,
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false,
        success: function () {
            updateTable(event, page);
            event.preventDefault();
        }
    })
}

function updateTable(event, page) {
    $.ajax({
        type: 'get',
        url: "http://localhost:8080/courses/members/" + courseId + "?page=" + page,
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false,
    }).done(function (data) {
        fillTable(data);
    });
}

function fillTable(data) {
    $('#membersTable tfoot').empty();

    $.each(data, function (index, element) {
        $('#membersTable tfoot').append(
            fillUserInformation(element) +
            '<td>' +
            '<a style="cursor: pointer" id="delete" value="' + element.id + '">' + 'delete' + '</a>' +
            '</td></tr>'
        );
    });
}
