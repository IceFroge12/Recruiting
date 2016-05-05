/**
 * Created by dima on 30.04.16.
 */

function menuController($scope) {
    $scope.main = function () {
        $location.path("/admin/main");
    };

    $scope.gettocoursesnc = function () {
        $location.path("/getToCourses");
    };

    $scope.home = function () {
        $location.path("/home");
    };
}

angular.module('appMenu')
    .controller('menuController', ['$scope', menuController]);