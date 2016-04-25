$(document).ready(function () {


    $.ajax({
        url: 'recruitment',
        type: 'POST',
        success: function (data) {
            console.log(data);
            var endRegistration = new Date(parseInt(data.registrationDeadline)).getDate();
            var startInterview = new Date(parseInt(data.startDate)).getDate();
            var endRecruiment = new Date(parseInt(data.endDate)).getDate();
            var registrationDeadline = new Date(parseInt(data)).getMonth();
            $("#endregistration").text(endRegistration)
            $("#startinterview").text(startInterview)
            $("#endrecruiment").text(endRecruiment)

            // var dateString = "\/Date(1334514600000)\/".substr(6);
            // var currentTime = new Date(parseInt(dateString ));
            // var month = currentTime.getMonth() + 1;
            // var day = currentTime.getDate();
            // var year = currentTime.getFullYear();
            // var date = day + "/" + month + "/" + year;
        }
    });


    
    
});