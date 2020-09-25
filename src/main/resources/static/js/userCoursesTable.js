$(document).ready(function () {

    $.getScript('/js/dataTable.js', function () {

        var table = $('#userCoursesTable');

        table.on("click", "#quizzes", (function () {
            var courseId = $(this).parent().find('#quizzes').attr('value');
            var userId = $('#userId').attr('value');
            window.location.href = "/userPanel/" + userId + "/quizzes?courseId=" + courseId;
        }));

    });

});

function updateTable(event, page) {
    userId = $('#userId').attr('value');

    $.ajax({
        type: 'get',
        url: "http://localhost:8080/userPanel/courses/" + userId + "?page=" + page,
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false,
    }).done(function (data) {
        fillTable(data);
    });
}

function fillTable(data) {
    $('#userCoursesTable tfoot').empty();

    $.each(data, function (index, element) {
        $('#userCoursesTable tfoot').append(
            '<tr><td>' + element.name + '</td>' +
            '<td>' + element.classification.name + '</td>' +
            '<td>' +
            '<a style="cursor: pointer" id="quizzes" value="' + element.id + '">' + 'quizzes' + '</a>' +
            '</td></tr>'
        );
    });
}
