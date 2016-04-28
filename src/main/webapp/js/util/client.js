$(function () {
    $('#calendar').fullCalendar({
        height: 420
    })
});

$(function () {
    $('#students_table').DataTable({
        "pagingType": "full_numbers"
    })
});

$(document).ready(function () {
    $('#staff_table').DataTable({
        "pagingType": "full_numbers"
    })
});

// $(function () {
//     $("input,select,textarea").not("[type=submit]").jqBootstrapValidation();
// });