/**
 * Created by dima on 30.04.16.
 */

'use strict';

function authorizationController($scope, TokenStorage, $http, $rootScope, $location) {

    $rootScope.authenticated = false;

    $scope.authsuccess = false;

    $scope.login = function () {

        if($scope.password === undefined){
            console.log("Auth error");
        }else{
        $http({
            method: 'POST',
            url: '/loginIn',
            contentType: 'application/json',
            data: {email: $scope.email, password: $scope.password}
        }).success(function (data, status, headers) {
            TokenStorage.store(headers('X-AUTH-TOKEN'));
            $rootScope.username = data.username;
            $rootScope.id = data.id;
            $rootScope.role = data.role;
            $location.path(data.redirectURL);
        }).error(function (data, status, headers) {
            console.log(data);
            if(status===401){
                $scope.authsuccess=true;
            }
        });
        }
    };

    $scope.registration = function () {
        console.log("registation");
        $location.path('/registration');
    };


    $scope.recoverPassword = function () {
        console.log("recoverPassword");
        $location.path('/recoverPasswordRequest');
    }
}

angular.module('appAuthorization')
    .controller('authorizationController', ['$scope', 'TokenStorage', '$http', '$rootScope', '$location', authorizationController]);