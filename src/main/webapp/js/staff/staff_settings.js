/**
 * Created by uakruk on 5/6/16.
 */
$(document).ready(function() {
    
    $("#savepass").click(function(event) {
        var oldPassword = $("#oldpassword").val();
        var newPassword = $("#newpassword").val();
        
        var userData = {oldPassword : oldPassword, newPassword: newPassword};
        
        $.ajax({
            url: 'changepass', 
            type: 'POST',
            data: userData,
            success: function (data) {
                console.log(data);
            }
        });
    });
});
