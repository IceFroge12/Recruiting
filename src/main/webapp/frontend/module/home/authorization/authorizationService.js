/**
 * Created by dima on 30.04.16.
 */
'use strict';

function authorizationService($http) {

    var service = {};
    
    service.loginIn = function (email, password) {
        $http({
            method : 'POST',
            url : '/loginIn',
            contentType: 'application/json',
            data: {email: email, password : password}
        // })
        //     .then(function successCallback(response) {
        //     console.log(response);
        //     TokenStorage.store(headers('X-AUTH-TOKEN'));
        //     $rootScope.authenticated = true;
        // }, function errorCallback(response) {
        //     console.log(response);
        });
    };
    
    return service;
}

angular.module('appAuthorization')
    .service('authorizationService', ['$http', authorizationService])