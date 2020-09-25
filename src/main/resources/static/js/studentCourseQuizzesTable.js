$(document).ready(function () {

    $.getScript('/js/dataTable.js', function () {

    });

});

function updateTable(event, page) {
    courseId = $('#courseId').attr('value');

    $.ajax({
        type: 'get',
        url: "http://localhost:8080/userPanel/quiz?courseId=" + courseId + "&&page=" + page,
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false,
    }).done(function (data) {
        fillTable(data);
    });
}

function fillTable(data) {
    user = $('#user').attr('value');
    userId = $('#userId').attr('value');
    courseId = $('#courseId').attr('value');

    $('#courseQuizzesTable tfoot').empty();

    $.each(data, function (index, element) {
        if (element.enabled) {
            $('#courseQuizzesTable tfoot').append('<tr>')
            $('#courseQuizzesTable tfoot').append(
                '<td>' + element.topic + '</td>' +
                '<td>' + element.description + '</td>' +
                '<td>' + element.duration + '</td>' +
                '<td>' + element.start + '</td>' +
                '<td>' + element.end + '</td>' +
                '<td>' + element.creator.name + " " + element.creator.family + '</td>'
            );

            if (element.participants[0] === undefined || element.participants[0].answersOfQuestions.length === 0) {
                $('#courseQuizzesTable tfoot').append(
                    '<td>' +
                    '<a style="cursor: pointer" id="start" href="' + "/userPanel/quiz/" + element.id + "/start?userId=" + userId + "&&courseId=" + courseId + '">' + 'start' + '</a>' +
                    '</td></tr>'
                )
            } else {
                $('#courseQuizzesTable tfoot').append(
                    '<td>Total Score = ' + element.participants[0].totalScore + '</td></tr>'
                )
            }
        }
    });

}
