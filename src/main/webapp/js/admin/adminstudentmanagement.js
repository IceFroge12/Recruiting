$(document).ready(function () {

    $.ajax({
        url: 'getallstudent',
        type: 'POST',
        data: 'json',
        success: function (data) {
            var formAnswer = JSON.parse(data);
            console.log(formAnswer.user);
            $.each(formAnswer, function (i, item){

            });
        }
    });


});