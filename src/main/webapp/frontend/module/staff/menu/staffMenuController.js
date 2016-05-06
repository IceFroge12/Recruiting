/**
 * Created by Vova on 02.05.2016.
 */

function staffMenuController($scope, $location) {
    $scope.main = function () {
        $location.path("/staff/main");
    };

    $scope.studentManagement = function () {
        $location.path("/staff/studentManagement");
    };

    $scope.scheduling = function () {
        $location.path("/staff/scheduling");
    };

    $scope.personal = function () {
        $location.path("/staff/personal");
    };
}

angular.module('appStaffMenu')
    .controller('staffMenuController', ['$scope', '$location', staffMenuController]);
