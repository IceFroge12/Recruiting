/**
 * Created by Vova on 02.05.2016.
 */

function staffMainController($scope, staffMainService) {
    staffMainService.loadRecruitmentData().then(function success(data) {
        console.log("JS")
        $scope.reqruitment = data.data;
        console.log($scope.reqruitment);
    }, function error() {
        console.log("error");
    });
}

angular.module('appStaffMain')
    .controller('staffMainController', ['$scope','staffMainService', staffMainController]);