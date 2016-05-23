/**
 * Created by dima on 30.04.16.
 */
'use strict';

angular.module('appAuthorization', ['angular-loading-bar'])
    .factory('TokenStorage', function ($rootScope) {
        var storageToken = 'auth_token';
        return {
            store: function (token) {
                $rootScope.authenticated = true;
                return localStorage.setItem(storageToken, token);
            },
            retrieve: function () {
                return localStorage.getItem(storageToken);
            },
            clear: function () {
                $rootScope.authenticated = false;
                $rootScope.username = null;
                $rootScope.id = null;
                return localStorage.removeItem(storageToken);
            }
        };
    }).factory('TokenAuthInterceptor', function ($q, TokenStorage, $location) {
    return {
        request: function (config) {
            var authToken = TokenStorage.retrieve();
            if (authToken) {
                config.headers['X-AUTH-TOKEN'] = authToken;
            }
            return config;
        },
        responseError: function (error) {
            if (error.status === 403) {
                $location.path('/accessDenied');
            }else if (error.status === 500){
                $location.path("/serverError");
            }else if (error.status === 504){
                alert(error.data.message);
            }
            return $q.reject(error);
        }
    };
});