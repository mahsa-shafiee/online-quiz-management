$(document).ready(function () {

    $.getScript('/js/dataTable.js', function () {

        var table = $('#courseQuizzesTable');

        table.on("click", "#stop", (function () {
            var quizId = $(this).parent().find('#stop').attr('value');
            stopQuiz(event, page, quizId);
        }));

        table.on("click", "#delete", (function () {
            var quizId = $(this).parent().find('#delete').attr('value');
            deleteQuiz(event, page, quizId);
        }));

        $("#add-quiz").submit(function (event) {
            addNewQuiz(event, page);
        });

    });

});

function deleteQuiz(event, page, quizId) {
    $.ajax({
        type: 'delete',
        url: "http://localhost:8080/userPanel/quiz/" + quizId + "/delete",
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false,
    }).done(function () {
        updateTable(event, page);
        event.preventDefault();
    })
}

function stopQuiz(event, page, quizId) {
    $.ajax({
        type: 'get',
        url: "http://localhost:8080/userPanel/quiz/" + quizId + "/stop",
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false,
    }).done(function () {
        updateTable(event, page);
        event.preventDefault();
    })
}

function validateStr(str) {
    return !(!str.match(/^[a-zA-Z ]*$/) || str.length < 3 || str.length > 20);
}

function showErrorMessage(error, event) {
    document.getElementById("error").innerHTML = error;
    $("#error").removeClass('alert-info');
    $("#error").addClass('alert-danger');
    event.preventDefault();
}

function addNewQuiz(event, page) {
    var quiz = {}
    var creator = {}
    var course = {}

    if (!validateStr($('#topic').val())) {
        showErrorMessage("Topic should be alphabetic and more than 3 characters!", event);
        return;
    }
    if (!validateStr($('#description').val())) {
        showErrorMessage("Description should be alphabetic and more than 3 characters!", event);
        return;
    }
    if ($('#start').val()>$('#end').val()){
        showErrorMessage("Start date should be less than end date!", event);
        return;
    }

    quiz["topic"] = $('#topic').val();
    quiz["description"] = $('#description').val();
    quiz["duration"] = $('#duration').val();
    quiz["start"] = $('#start').val().replace('T', ' ');
    quiz["end"] = $('#end').val().replace('T', ' ');
    creator["id"] = $('#userId').val();
    course["id"] = $('#courseId').val();
    quiz["creator"] = creator;
    quiz["course"] = course;

    $.ajax({
        type: 'post',
        url: "http://localhost:8080/userPanel/quiz/add",
        contentType: "application/json",
        data: JSON.stringify(quiz),
        dataType: "json",
        async: false,
        cache: false,
        success: function () {
            showMessage("The quiz added.");
            updateTable(event, page);
            event.preventDefault();
        }
    })
}

function showMessage(message) {
    document.getElementById("error").innerHTML = message;
    $("#error").removeClass('alert-danger');
    $("#error").addClass('alert-info');
}

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
    userId = $('#userId').attr('value');
    $('#courseQuizzesTable tfoot').empty();

    $.each(data, function (index, element) {
        $('#courseQuizzesTable tfoot').append('<tr>')
        $('#courseQuizzesTable tfoot').append(
            '<td>' + element.topic + '</td>' +
            '<td>' + element.description + '</td>' +
            '<td>' + element.duration + '</td>' +
            '<td>' + element.start + '</td>' +
            '<td>' + element.end + '</td>' +
            '<td>' + element.creator.name + " " + element.creator.family + '</td>' +
            '<td>' +
            '<a id="participants" href="' + "/userPanel/quiz/" + element.id + "/participants" + '">' + 'participants' + '</a>' +
            '</td>'
        );
        if (element.creator.id === userId - 0) {
            $('#courseQuizzesTable tfoot').append(
                '<td>' +
                '<a id="update" href="' + "/userPanel/quiz/" + element.id + "/update" + '">' + 'update' + '</a>' +
                '</td>' +
                '<td>' +
                '<a style="cursor: pointer" id="stop" value="' + element.id + '">' + 'stop' + '</a>' +
                '</td>' +
                '<td>' +
                '<a style="cursor: pointer" id="delete" value="' + element.id + '">' + 'delete' + '</a>' +
                '</td>' +
                '<td>' +
                '<a id="questions" href="' + "/userPanel/questions?quizId=" + element.id + '">' + 'edit questions' + '</a>' +
                '</td></tr>'
            )
        } else {
            $('#courseQuizzesTable tfoot').append('</tr>')
        }
    });
}
