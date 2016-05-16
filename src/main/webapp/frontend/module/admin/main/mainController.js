/**
 * Created by dima on 30.04.16.
 */

function mainController($scope, mainService) {

    mainService.loadRecruitmentData().then(function success(data) {
        $scope.reqruitment = data.data;
        console.log($scope.reqruitment);
        var endRegistration = new Date(parseInt($scope.reqruitment.registrationDeadline)).toDateString();
        var endRegistrationDay = new Date(parseInt($scope.reqruitment.registrationDeadline)).getDate();
        var startInterview = new Date(parseInt($scope.reqruitment.startDate)).getDate();
        var endRecruitment = new Date(parseInt($scope.reqruitment.endDate)).getDate();

        var currentDate = new Date().getDate();
        var endDayRegistration = endRegistrationDay - parseInt(currentDate);
        var scheduleDeadline = new Date(parseInt($scope.reqruitment.scheduleChoicesDeadline)).toDateString();
        var startDayInterview = startInterview - parseInt(currentDate);
        $scope.toRegEnding = endDayRegistration;
        $scope.toTheInterviewStart = startDayInterview;
        $scope.toTheEndOfRecruitment = endRecruitment;
    }, function error() {
        console.log("error");
    });

    mainService.getCurrentRecruitmentStudents().then(function success(data) {
        $scope.newRegistrations = data.data;
    });

}

angular.module('appMain')
    .controller('mainController', ['$scope', 'mainService', mainController]);