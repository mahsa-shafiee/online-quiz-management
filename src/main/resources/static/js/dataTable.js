var page = 1;

$("#prevPage").addClass('inactiveLink');
updateTable(event,page);

$("#nextPage").on("click", function () {
    page = page + 1;
    updateTable(event,page);
    $("#prevPage").removeClass('inactiveLink');
    return false;
});


$("#prevPage").on("click", function () {
    if (page > 1) {
        page = page - 1;
        updateTable(event,page);
        if (page === 1) {
            $("#prevPage").addClass('inactiveLink');
        }
    } else {
        $("#prevPage").addClass('inactiveLink');
    }
    return false;
});

function fillUserInformation(element) {
    return '<tr>' +
        '<td>' + element.id + '</td>' +
        '<td>' + element.name + '</td>' +
        '<td>' + element.family + '</td>' +
        '<td>' + element.emailAddress + '</td>' +
        '<td>' + element.password + '</td>' +
        '<td>' + element.role.toLowerCase() + '</td>' +
        '<td> ' + element.registrationStatus.toLowerCase() + '</td>';
}