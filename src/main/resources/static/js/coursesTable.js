$(document).ready(function () {

    $.getScript('/js/dataTable.js', function () {

        var table = $('#coursesTable');

        $("#add-course").submit(function (event) {
            if (!validateCourseName()) {
                showMessage("Name should be alphabetic and more than 3 characters!");
                event.preventDefault();
            } else
                addNewCourse(event, page);
        });

        table.on("click", "#members", (function () {
            var courseId = $(this).parent().find('#members').attr('value');
            window.location.href = "/adminPanel/course/members/" + courseId;
        }));

        table.on("click", "#delete", (function () {
            var courseId = $(this).parent().find('#delete').attr('value');
            deleteCourse(event, page, courseId);
        }));

    });

});

function validateCourseName() {
    var name = $("#name").val();
    return !((!name.match(/^[A-Za-z]+$/) && !name.match(/^[a-z0-9]+$/)) || name.length < 3 || name.length > 20);
}

function addNewCourse(event, page) {
    var course = {}
    var classification = {}

    course["name"] = $('#name').val();
    course["classification"] = classification;
    classification["name"] = $('#classification').val();

    $.ajax({
        type: 'post',
        url: window.location + "/add",
        contentType: "application/json",
        data: JSON.stringify(course),
        dataType: "json",
        async: false,
        cache: false,
        success: function () {
            showMessage("The course added.");
            updateTable(event, page);
            event.preventDefault();
        },
        error: function () {
            showMessage("There is no such classification!");
            updateTable(event, page);
            event.preventDefault();
        }
    })
}

function showMessage(message) {
    document.getElementById("msg").innerHTML = message;
    $('#msg').css("padding", "0");
}

function deleteCourse(event, page, courseId) {
    $.ajax({
        type: 'delete',
        url: window.location + "/delete?id=" + courseId,
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
        url: window.location + "/show?page=" + page,
        contentType: "application/json",
        dataType: "json",
        async: false,
        cache: false,
    }).done(function (data) {
        fillTable(data);
    });
}

function fillTable(data) {
    $('#coursesTable tfoot').empty();

    $.each(data, function (index, element) {
        $('#coursesTable tfoot').append(
            '<tr><td>' + element.id + '</td>' +
            '<td>' + element.name + '</td>' +
            '<td>' + element.classification.name + '</td>' +
            '<td>' +
            '<a style="cursor: pointer" id="delete" value="' + element.id + '">' + 'delete' + '</a>' +
            '</td>' +
            '<td>' +
            '<a style="cursor: pointer" id="members" value="' + element.id + '">' + 'members' + '</a>' +
            '</td>' +
            '<td>' +
            '<a id="update" href="' + "/adminPanel/courses/update/" + element.id + '">' + 'update' + '</a>' +
            '</td></tr>'
        );
    });
}
