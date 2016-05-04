/**
 * Created by dima on 30.04.16.
 */

'use strict';

function authorizationController($scope, TokenStorage, $http, $rootScope, $location) {
    $rootScope.authenticated = false;


    $scope.login = function () {
        $http({
            method: 'POST',
            url: '/loginIn',
            contentType: 'application/json',
            data: {email: $scope.email, password: $scope.password}
        }).success(function (data, status, headers) {
            TokenStorage.store(headers('X-AUTH-TOKEN'));
            $rootScope.authenticated = true;
            $rootScope.username = data.username;
            $rootScope.id = data.id;
            $location.path(data.redirectURL);
        }).error(function (data, status, headers) {
            console.log(data);
        });
    };
    
    $scope.registration = function () {
        console.log("registation");
        $location.path('/registration');
    }
}

angular.module('appAuthorization')
    .controller('authorizationController', ['$scope', 'TokenStorage', '$http', '$rootScope', '$location', authorizationController]);