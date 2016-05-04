/**
 * Created by dima on 30.04.16.
 */

'use strict';

function headerController($scope, $location) {

    $scope.loginPage = function () {
        $location.path("/authorization");
    }

}

angular.module('appHeader')
    .controller('headerController', ['$scope', '$location', headerController]);


