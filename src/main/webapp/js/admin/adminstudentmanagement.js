$(document).ready(function () {

    $.ajax({
        url: 'getallstudent',
        type: 'POST',
        data: 'json',
        async:false,
        success: function (data) {
        var appFormList = "["+data+"]";
            var appFormJson = JSON.parse(appFormList);
            console.log(appFormJson);

            $.each(appFormJson,function (i, item) {
                console.log(item.user.firstName);
                var $tr = $('<tr>').append(
                    $('<td>').text(item.user.firstName)
                    // $('<td>').text(item.firstName + " " + item.lastName),
                    // $('<td>').text(item.email),
                    // $('<td>').text(roleName),
                    // $('<td>').append(jQuery(delegate))
                ).appendTo("#students_table");
            });
        }
    });
});