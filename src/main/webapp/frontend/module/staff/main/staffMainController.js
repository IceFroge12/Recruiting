/**
 * Created by Vova on 02.05.2016.
 */

function staffMainController($scope, staffMainService) {
    staffMainService.loadRecruitmentData().then(function success(data) {
        $scope.reqruitment = data.data;
        var endDayRegistration = new Date(parseInt($scope.reqruitment.registrationDeadline)).toDateString();
        var scheduleDeadline = new Date(parseInt( $scope.reqruitment.scheduleChoicesDeadline)).toDateString();
    }, function error() {
        console.log("error");
    });
}

angular.module('appStaffMain')
    .controller('staffMainController', ['$scope','staffMainService', staffMainController]);