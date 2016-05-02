/**
 * Created by dima on 29.04.16.
 */
'use strict';

angular.module('app', [
    'ngRoute',
    'appHome',
    'appAdmin',
    'appMain',
    'appForm',
    'appFeedback'
]).config(function ($routeProvider) {

    $routeProvider
        .when('/home', {
            templateUrl: 'module/home/home.html',
            controller: 'homeController'
        })
        .when('/admin/main', {
            templateUrl: 'module/admin/main/main.html',
            controller: 'mainController'
        })
        .when('/admin/staffmanagement', {
            templateUrl: 'module/admin/staffmanagement/staffmanagement.html',
            controller: 'staffManagementController'
        })
            
        // .when('/admin', {
        //     templateUrl: 'module/admin/admin.html',
        //     controller: 'adminController'
        // })
        // .when('/admin', {
        //     templateUrl: 'module/admin/admin.html',
        //     controller: 'adminController'
        // })
        // .when('/admin', {
        //     templateUrl: 'module/admin/admin.html',
        //     controller: 'adminController'
        // })
        // .when('/admin', {
        //     templateUrl: 'module/admin/admin.html',
        //     controller: 'adminController'
        // })
        // .when('/admin', {
        //     templateUrl: 'module/admin/admin.html',
        //     controller: 'adminController'
        // })
        // .when('/admin', {
        //     templateUrl: 'module/admin/admin.html',
        //     controller: 'adminController'
        // })
        // .when('/admin', {
        //     templateUrl: 'module/admin/admin.html',
        //     controller: 'adminController'
        // })
        // .when('/admin', {
        //     templateUrl: 'module/admin/admin.html',
        //     controller: 'adminController'
        // })
        
        // STUDENT
        .when('/student/appform', {
            templateUrl: 'module/student/appform/appForm.html',
            controller: 'appFormController'
        })
        .when('/student/feedback', {
            templateUrl: 'module/student/feedback/feedback.html',
            controller: 'feedbackController'
        })
        

        .otherwise({
            redirectTo: '/'
        });
});
