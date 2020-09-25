$(document).ready(function () {

    $.getScript('/js/dataTable.js', function () {

        var table = $('#quizQuestionsTable');

        table.on("click", "#set-score", (function () {
            var quizId = $('#id').attr('value');
            var questionId = $(this).parent().find('#set-score').attr('value');
            setScoreForQuestion(event, page, questionId, quizId);
        }));

        $("#new-question").on('click', function (event) {
            bootbox.dialog({
                title: '<h3>Add new question</h3>',
                message: "<p>Please choose question type:</p>",
                size: 'large',
                buttons: {
                    cancel: {
                        label: "Descriptive Question",
                        className: 'btn-info',
                        callback: function () {
                            var quizId = $('#id').attr('value');
                            window.location.href = "/userPanel/question/addDescriptive?quizId=" + quizId;
                        }
                    },
                    ok: {
                        label: "Multiple Choice Question",
                        className: 'btn-info',
                        callback: function () {
                            var quizId = $('#id').attr('value');
                            window.location.href = "/userPanel/question/addMultipleChoice?quizId=" + quizId;
                        }
                    }
                }
            });
            event.preventDefault();
        });

        $("#question-bank").on('click', function (event) {
            var quizId = $('#id').attr('value');
            window.location.href = "/userPanel/question/addFromBank?quizId=" + quizId;
            event.preventDefault();
        });
    });

});

function setScoreForQuestion(event, page, questionId, quizId) {
    bootbox.prompt({
        title: "<p>Enter score</p>",
        inputType: 'number',
        callback: function (result) {
            console.log(result);

            $.ajax({
                type: 'get',
                url: "http://localhost:8080/userPanel/question/" + questionId + "/" + quizId + "/setScore?score=" + result,
                contentType: "application/json",
                dataType: "json",
                async: false,
                cache: false,
            }).done(function (data) {
                updateTable(event, page)
            });
        }
    });
}

function updateTable(event, page) {
    var quizId = $('#id').attr('value');

    $.ajax({
        type: 'get',
        url: "http://localhost:8080/userPanel/question/" + quizId + "?page=" + page,
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false,
    }).done(function (data) {
        fillTable(data);
    });
}

function fillTable(data) {
    $('#quizQuestionsTable tfoot').empty();

    $.each(data, function (index, element) {

        $('#quizQuestionsTable tfoot').append(
            '<tr><td>' + element.id + '</td>' +
            '<td>' + element.title + '</td>' +
            '<td>' + element.content + '</td>' +
            '<td>' + element.quizzes[0].score + '</td>' +
            '<td>' +
            '<a style="cursor: pointer" id="set-score" value="' + element.id + '">' + 'define score' + '</a>' +
            '</td></tr>'
        );

    });
}
