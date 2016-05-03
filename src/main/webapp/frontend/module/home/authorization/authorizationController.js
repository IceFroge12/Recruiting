/**
 * Created by dima on 30.04.16.
 */

'use strict';

function authorizationController($scope, $rootscope, authorizationService) {
    $rootscope.authenticated = false;


    $scope.login = function () {
        authorizationService.loginIn($scope.email, $scope.password);
    }
    
}

angular.module('appAuthorization')
    .controller('authorizationController', ['$scope', 'authorizationService', authorizationController]);