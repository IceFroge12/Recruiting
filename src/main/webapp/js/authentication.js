/**
 * Created by IO on 24.04.2016.
 */

$(document).ready(function () {
    
    $("#loginButton").click(function (event) {
        var username = $("#username").val();
        var password = $("#password").val();
        
        var user = {username: username, password: password};

        
        $.ajax({
            url: 'loginIn',
            dataType: "json",
            type: 'POST',
            data: user,
            success: function (data) {
                console.log(data);
            }

        })
        
    });

});