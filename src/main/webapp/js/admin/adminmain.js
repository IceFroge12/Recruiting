$(document).ready(function () {
    
    $.ajax({
        url: 'recruitment',
        type: 'POST',
        success: function (data) {
  
            var endRegistration = new Date(parseInt(data.registrationDeadline)).toDateString();
            var endRegistrationDay = new Date(parseInt(data.registrationDeadline)).getDate();
            var startInterview = new Date(parseInt(data.startDate)).getDate();
            var endRecruitment = new Date(parseInt(data.endDate)).getDate();

            var currentDate = new Date().getDate();
            var endDayRegistration = endRegistrationDay - parseInt(currentDate);
            var scheduleDeadline = new Date(parseInt(data.scheduleChoicesDeadline)).toDateString();
            var startDayInterview = startInterview - parseInt(currentDate);

            $("#endregistration").text(endDayRegistration);

            $("#startinterview").text(startDayInterview);

            $("#endrecruiment").text(endRecruitment);

            $("#registration-deadline").text(endRegistration);

            $("#schedule-deadline").text(scheduleDeadline);

        }
    });

});