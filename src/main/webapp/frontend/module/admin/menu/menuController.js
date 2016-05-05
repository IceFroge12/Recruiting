/**
 * Created by dima on 30.04.16.
 */

function menuController($scope, $location) {
    $scope.mainAdmin = function () {
        $location.path("/admin/main");
    };

    $scope.staffManAdmin = function () {
        $location.path("/admin/staffmanagement");
    };

    $scope.studManAdmin = function () {
        $location.path("/admin/studentmanagement");
    };

    $scope.shedulAdmin = function () {
        $location.path("/admin/scheduling");
    };

    $scope.reportsAdmin = function () {
        $location.path("/admin/report");
    };
}

angular.module('appMenu')
    .controller('menuController', ['$scope','$location', menuController]);