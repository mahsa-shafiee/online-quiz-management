$(document).ready(function () {

    $.getScript('/js/dataTable.js', function () {

        var table = $('#questionsTable');

        table.on("click", "#choose", (function () {
            var questionId = $(this).parent().find('#choose').attr('value');
            addNewQuestion(event, page, questionId);
        }));

    });

});

function addNewQuestion(event, page, questionId) {
    var quizId = $('#id').attr('value');

    $.ajax({
        type: 'get',
        url: "http://localhost:8080/userPanel/question/" + quizId + "/choose/" + questionId,
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false,
    }).done(function () {
        window.location.href = "/userPanel/questions?quizId=" + quizId;
        event.preventDefault();
    })
}

function updateTable(event, page) {
    var quizId = $('#id').attr('value');

    $.ajax({
        type: 'get',
        url: "http://localhost:8080/userPanel/question/" + quizId + "/showBank?page=" + page,
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false,
    }).done(function (data) {
        fillTable(data);
    });
}

function fillTable(data) {
    $('#questionsTable tfoot').empty();

    $.each(data, function (index, element) {
        console.log(4)
        $('#questionsTable tfoot').append(
            '<tr><td>' + element.id + '</td>' +
            '<td>' + element.title + '</td>' +
            '<td>' + element.content + '</td>' +
            '<td>' +
            '<a style="cursor: pointer" id="choose" value="' + element.id + '">' + 'choose this question' + '</a>' +
            '</td></tr>'
        );
    });
}
