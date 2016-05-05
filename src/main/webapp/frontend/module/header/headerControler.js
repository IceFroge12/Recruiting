/**
 * Created by dima on 30.04.16.
 */

'use strict';

function headerController($scope, $location, TokenStorage) {
    
    $scope.loginPage = function () {
        $location.path("/authorization");
    };

    $scope.logOut = function () {
        TokenStorage.clear();
        $location.path('/home');
    };
}

angular.module('appHeader')
    .controller('headerController', ['$scope', '$location', 'TokenStorage', headerController]);


