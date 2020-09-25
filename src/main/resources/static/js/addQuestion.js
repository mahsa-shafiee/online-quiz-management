$(document).ready(function () {

    $("#add-option").on('click', function (event) {
        event.preventDefault();
        bootbox.prompt({
            title: "<p>Enter text of option</p>",
            inputType: 'textarea',
            callback: function (result) {
                if (result.length !== 0) {
                    fillOptions(result);
                }
            }
        });
    });

    $('#add-in-question-bank').change(function () {
        $('#myForm').submit();
        $('#submit').click();
        $("#submit").submit();
        $("#submit").click();
        $("#myForm").submit();
    })

    $('#myForm').one('submit', function (event) {
        event.preventDefault();
        bootbox.dialog({
            message: "<p>Do you want to add this question to course classification question bank?</p>",
            size: 'large',
            buttons: {
                ok: {
                    callback: function () {
                        $("#add-in-question-bank").val('true');
                        $('#myForm').submit();
                        $('#add-in-question-bank').val('true')
                            .trigger('change');
                    }
                },
                cancel: {
                    callback: function () {
                        $("#add-in-question-bank").val('false');
                        $('#add-in-question-bank').val('false')
                            .trigger('change');
                    }
                }
            }
        });

    });
});

var number = 1;

function fillOptions(data) {
    $('#optionsTable').append(
        '<tr><td>' + number + '</td>' +
        '<td><input name="correct" id="correct" value="' + data + '" type="radio"/>' +
        '<pre style="background:transparent; display: inline-table; border:0"> ' + data + '</pre>' +
        '</td></tr>'
    );
    number++;
    var options = $("#options");
    options.val(options.val() + data + ',');
}
