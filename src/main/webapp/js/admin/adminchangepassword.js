$(document).ready(function () {


    $("#savePassword").click(function(event){

        var oldPassword = $("#oldpassword").val();
        var newPassword = $("#newpassword").val();

        var userData = {oldPassword: oldPassword, newPassword: newPassword};

        $.ajax({
            url: 'changepass',
            type: 'POST',
            data: userData,
            success: function (data) {

            }
        });
    });



});