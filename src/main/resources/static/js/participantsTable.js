$(document).ready(function () {

    $.getScript('/js/dataTable.js', function () {

        $('#count').append($('#participantsCount').attr('value'));

        $('#search-user').on("click", "#search", (function (event) {
            updateTable(event, page, true);
        }));

        $('#usersTable').on("click", "#showResult", (function (event) {
            var userId = $(this).parent().find('#showResult').attr('value');
            showResult(event, userId);
        }));

    });

});

function showResult(event, userId) {
    quizId = $('#quizId').attr('value');
    window.location.href = "http://localhost:8080/userPanel/quiz/" + quizId + "/result?userId=" + userId;
}

function updateTable(event, page, isSubmit) {
    var name = $('#name').val();
    var family = $('#family').val();
    var emailAddress = $('#emailAddress').val();

    $.ajax({
        type: 'get',
        url: window.location + "/get?page=" + page + "&&name=" + name + "&&family=" + family
            + "&&emailAddress=" + emailAddress,
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
            '<tr><td>' + element.name + '</td>' +
            '<td>' + element.family + '</td>' +
            '<td>' + element.emailAddress + '</td>' +
            '<td>' +
            '<a style="cursor: pointer" id="showResult" value="' + element.id + '">' + 'result' + '</a>' +
            '</td></tr>'
        );
    });
}