$(document).ready(function () {
   
    $.ajax({
        url: 'getallstudent',
        type: 'POST',
        data: 'json',
        success: function (data) {
            console.log(data);
        }
    });



});