/**
 * Created by dima on 29.04.16.
 */
'use strict';

angular.module('app', [
    'ngRoute',
    'appHome',
    'appAdmin'
]).config(function ($routeProvider) {

    $routeProvider
        .when('/home', {
            templateUrl: 'module/home/home.html',
            controller: 'homeController'
        })
        .when('/admin', {
            templateUrl: 'module/admin/admin.html',
            controller: 'adminController'
        })
        .otherwise({
            redirectTo: '/'
        });
});
