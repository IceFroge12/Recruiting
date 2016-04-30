/**
 * Created by dima on 30.04.16.
 */

'use strict';

var app = angular.module('appAdmin', ['appHeader', 'appStudentManagement','appMenu','appFooter']);
    // .config(function ($routeProvider) {
    //
    //     $routeProvider
    //         .when('/staffmanagement', {
    //             templateUrl: 'module/admin/staffmanagement/staffmanagement.html',
    //             controller: 'staffManagementController'
    //         })
    //         .otherwise({
    //             redirectTo: '/'
    //         });
    // });

app.directive('appHeader', function () {
    return {
        templateUrl: 'module/header/header.html',
        controller: 'headerController'
    };
});

app.directive('appStudentManagement', function () {
    return {
        templateUrl: 'module/admin/studentmanagement/studentmanagement.html',
        controller: 'studentManagementController'
    };
});

app.directive('appMenu',function () {
    return {
        templateUrl: 'module/admin/menu/menu.html',
        controller: 'menuController'
    };
});

app.directive('appFooter',function () {
    return {
        templateUrl: 'module/footer/footer.html',
        controller: 'footerController'
    };
});





