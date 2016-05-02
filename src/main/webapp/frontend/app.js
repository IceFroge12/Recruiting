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
    'appFeedback',
    'appStaffMain',
    'appStaffPersonal',
    'appStaffScheduling',
    'appStaffStudentManagement'
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

        //STAFF
        .when('/staff/main', {
            templateUrl: 'module/staff/main/staffMain.html',
            controller: 'staffMainController'
        })

        .when('/staff/studentManagement', {
            templateUrl: 'module/staff/studentmanagement/staffStudentManagement.html',
            controller: 'staffStudentManagementController'
        })

        .when('/staff/scheduling', {
            templateUrl: 'module/staff/scheduling/staffScheduling.html',
            controller: 'staffSchedulingController'
        })

        .when('/staff/personal', {
            templateUrl: 'module/staff/personal/staffPersonal.html',
            controller: 'staffPersonalController'
        })

        // STUDENT
        .when('/student/appform', {
            templateUrl: 'module/student/appform/appForm.html',
            controller: 'appFormController'
        })
        .when('/student/feedback', {
            templateUrl: 'module/student/feedback/feedback.html',
            controller: 'feedbackController'
        })
        .when('/student/scheduling', {
            templateUrl: 'module/student/scheduling/studentScheduling.html',
            controller: 'studentSchedulingController'
        })

        .otherwise({
            redirectTo: '/'
        });
});
