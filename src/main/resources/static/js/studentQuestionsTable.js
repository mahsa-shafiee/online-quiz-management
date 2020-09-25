$(document).ready(function () {

    $.getScript('/js/dataTable.js', function () {
        timer();
    });

});

function saveAnswer(page) {
    var answer;

    if ($('#answer').val() !== 'undefined' && $('#answer').val().length > 0) {
        console.log(555);
        answer = $('#answer').val();
    } else {
        console.log(99);
        answer = $('#choice').attr('value');
    }
    quizId = $('#quizId').attr('value');
    userId = $('#userId').attr('value');

    $.ajax({
        type: 'get',
        url: "http://localhost:8080/userPanel/quiz/" + quizId + "/save?userId=" + userId
            + "&&number=" + page + "&&answer=" + answer,
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false
    })
}

function updateTable(event, page) {
    quizId = $('#quizId').attr('value');
    userId = $('#userId').attr('value');
    courseId = $('#courseId').attr('value');

    $.ajax({
        type: 'get',
        url: "http://localhost:8080/userPanel/quiz/" + quizId + "?userId=" + userId + "&&number=" + page,
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            $('#next').empty();
            $('#next').append("Next");
            fillTable(data, page);
        },
        error: function () {
            $('#next').empty();
            $('#next').append("Finish");
            window.location.href = "http://localhost:8080/userPanel/" + userId + "/quizzes?courseId=" + courseId;
        }
    })
}

function fillTable(data, page) {

    $('#question').empty();

    $('#question').append(
        "Number " + page +
        ":<br>Title: " +
        data.title +
        "<br>Content: " +
        data.content + "<br>" +
        data.quizzes[0].score + " Points<br>"
    );

    if (typeof data.options !== 'undefined') {
        data.options.forEach(function (entry) {
            $('#question').append("<input type=\"radio\" name=\"choice\" id=\"choice\" value='+" + entry + "+'>" + entry);
        });
        $('#answer textarea').attr('hidden', '"hidden"');
    } else {
        $('#answer').attr('rows', data.requiredAnswerLines);
        $('#answer').attr('maxlength', 40 * data.requiredAnswerLines);
        $('#answer').show();
    }
}

function timer() {

    var end = new Date();
    duration = $('#duration').attr('value');
    end.setTime(end.getTime() + (duration * (60000)));

    var _second = 1000;
    var _minute = _second * 60;
    var _hour = _minute * 60;
    var timer;

    function showRemaining() {
        var now = new Date();
        var distance = end - now;
        if (distance < 0) {

            userId = $('#userId').attr('value');
            courseId = $('#courseId').attr('value');

            clearInterval(timer);
            document.getElementById('clockDiv').innerHTML = ' ';
            $('#next').empty();
            $('#next').append("Finish");
            window.location.href = "http://localhost:8080/userPanel/" + userId + "/quizzes?courseId=" + courseId;

            return;
        }
        var minutes = Math.floor((distance % _hour) / _minute);
        var seconds = Math.floor((distance % _minute) / _second);

        document.getElementById('minutes').innerHTML = minutes + "";
        document.getElementById('seconds').innerHTML = seconds + "";
    }

    timer = setInterval(showRemaining, 0);

}