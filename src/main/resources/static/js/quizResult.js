$(document).ready(function () {

    $.getScript('/js/dataTable.js', function () {

        $('#set-score').on('click', function (event) {
            var quizId = $('#quizId').attr('value');
            var score = $('#set-score').attr('value');
            setScore(event, page, quizId, score);
        });

    });

});

function f() {
    var quizId = $('#quizId').attr('value');
    var score = $('#set-score').attr('value');
    setScore(event, page, quizId, score);
}

function setScore(event, page, quizId, score) {
    bootbox.prompt({
        title: "<p>Enter score</p>",
        inputType: 'number',
        step: '0.25',
        callback: function (result) {
            console.log(result);
            if (result <= score) {
                userId = $('#userId').attr('value');
                $.ajax({
                    type: 'get',
                    url: "http://localhost:8080/userPanel/quiz/" + quizId + "/" + userId + "/setScore?score=" + result,
                    contentType: "application/json",
                    dataType: "json",
                    async: false,
                    cache: false,
                }).done(function (data) {
                    alert("Score saved.")
                    updateTable(event, page+1)
                });
            } else alert("It is more than question score!")
        }
    });
}

function updateTable(event, page) {
    quizId = $('#quizId').attr('value');
    userId = $('#userId').attr('value');

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
            window.location.href = "http://localhost:8080/userPanel/quiz/" + quizId + "/participants";
        }
    })
}

function getAnswer(event, page) {
    quizId = $('#quizId').attr('value');
    userId = $('#userId').attr('value');

    $.ajax({
        type: 'get',
        url: "http://localhost:8080/userPanel/quiz/" + quizId + "/answer?userId=" + userId,
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false
    }).done(function (data) {
        $('#question').append("Student answer :<br>" + data.answersOfQuestions[page]);
    });
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
        $('#question').append("Options :");
        data.options.forEach(function (entry) {
            $('#question').append("<br><p name=\"option\" id=\"option\" value='+" + entry + "+'>" + entry);
        });
        getAnswer(event, page);
    } else {
        getAnswer(event, page);
        $('#question').append("<br>" +
            "<a style='cursor: pointer' id='set-score' onclick='f()' value=" + data.quizzes[0].score + ">SET SCORE</a>"
        );
    }

}
