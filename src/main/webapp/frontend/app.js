/**
 * Created by dima on 29.04.16.
 */
'use strict';

angular.module('app', [
        'ngRoute',
        'ngFileUpload',
        'appHome',
        'appMain',
        'appFeedback',
        'appStaffMain',
        'appStaffPersonal',
        'appStaffScheduling',
        'appStaffStudentManagement',
        'appStaffManagement',
        'appStaffAppForm',
        'appStudentManagement',
        'appReport',
        'appScheduling',
        'appAdminForm',
        'appRecruitment',
        'appNotification',
        'appPersonal',
        'appStudentForm',
        'appStudentMenu',
        'studentScheduling',
        'appStudentSettings',
        'appAuthorization',
        'appRegistration',
        'appMenuMain',
        'appEducationNC',
        'appGetToCourses',
        'appRecoverRequestPage',
        'appRecoverPassword'
    ])

    //     .factory('TokenStorage', function () {
    //     var storageToken = 'auth_token';
    //     return {
    //         store: function (token) {
    //             return localStorage.setItem(storageToken, token);
    //         },
    //         retrieve: function () {
    //             return localStorage.getItem(storageToken);
    //         },
    //         clear: function () {
    //             return localStorage.removeItem(storageToken);
    //         }
    //     };
    // }).factory('TokenAuthInterceptor', function ($q, TokenStorage) {
    //     return {
    //         request: function (config) {
    //             var authToken = TokenStorage.retrieve();
    //             if (authToken) {
    //                 config.headers['X-AUTH-TOKEN'] = authToken;
    //             }
    //             return config;
    //         },
    //         responseError: function (error) {
    //             if (error.status === 401 || error.status === 403) {
    //                 TokenStorage.clear();
    //             }
    //             return $q.reject(error);
    //         }
    //     };
    // })
    //


    .controller('appController', function ($scope, $http, $rootScope) {
        $scope.init = function () {
            $http.get('/currentUser')
                .success(function (user) {
                    $rootScope.authenticated = true;
                    $rootScope.id = user.id;
                    $rootScope.username = user.firstName;
                });
        }
    })
    .config(function ($routeProvider, $httpProvider) {

        $httpProvider.interceptors.push('TokenAuthInterceptor');

        $routeProvider
            .when('/home', {
                templateUrl: 'module/home/home.html',
                controller: 'homeController'
            })
            .when('/getToCourses', {
                templateUrl: 'module/home/gettocourses/getToCourses.html',
                controller: 'getToCoursesController'
            })
            .when('/educationNC', {
                templateUrl: 'module/home/educationnc/educationNC.html',
                controller: 'educationNCController'
            })
            .when('/admin/main', {
                templateUrl: 'module/admin/view/main.html',
                controller: 'mainController'
            })
            .when('/admin/staffmanagement', {
                templateUrl: 'module/admin/view/staffmanagement.html',
                controller: 'staffManagementController'
            })
            .when('/admin/studentmanagement', {
                templateUrl: 'module/admin/view/studentmanagement.html',
                controller: 'studentManagementController'
            })
            .when('/admin/adminformsettings', {
                templateUrl: 'module/admin/view/formSettings.html',
                controller: 'formSettingsController'
            })
            .when('/admin/scheduling', {
                templateUrl: 'module/admin/view/scheduling.html',
                controller: 'schedulingController'
            })
            .when('/admin/report', {
                templateUrl: 'module/admin/view/report.html',
                controller: 'reportController'
            })
            .when('/admin/recruitment', {
                templateUrl: 'module/admin/view/recruitment.html',
                controller: 'recruitmentController'
            })
            .when('/admin/notification', {
                templateUrl: 'module/admin/view/notification.html',
                controller: 'notificationController'
            })
            .when('/admin/personal', {
                templateUrl: 'module/admin/view/personal.html',
                controller: 'personalController'
            })

            //STAFF
            .when('/staff/main', {
                templateUrl: 'module/staff/view/staffMain.html',
                controller: 'staffMainController'
            })

            .when('/staff/studentManagement', {
                templateUrl: 'module/staff/view/staffStudentManagement.html',
                controller: 'staffStudentManagementController'
            })

            .when('/staff/scheduling', {
                templateUrl: 'module/staff/view/staffScheduling.html',
                controller: 'staffSchedulingController'
            })

            .when('/staff/personal', {
                templateUrl: 'module/staff/view/staffPersonal.html',
                controller: 'staffPersonalController'
            })

            .when('/staff/appformstudent', {
                templateUrl: 'module/staff/view/appFormStudent.html',
                controller: 'appFormStudentController'
            })

            // STUDENT
            .when('/student/appform', {
                templateUrl: 'module/student/view/appForm.html',
                controller: 'appFormController'
            })
            .when('/student/feedback', {
                templateUrl: 'module/student/view/feedback.html',
                controller: 'feedbackController'
            })
            .when('/student/scheduling', {
                templateUrl: 'module/student/view/studentScheduling.html',
                controller: 'studentSchedulingController'
            })

            .when('/student/settings', {
                templateUrl: 'module/student/view/studentSettings.html',
                controller: 'studentSettingsController'
            })

            //Auth
            .when('/authorization', {
                templateUrl: 'module/home/authorization/authorization.html',
                controller: 'authorizationController'

            })

            .when('/registration', {
                templateUrl: 'module/home/registration/registration.html',
                controller: 'registrationController'
            })

            //recoverRequestPassword
            .when('/recoverPasswordRequest', {
                templateUrl: 'module/home/recoverRequestPage/recoverRequestPage.html',
                controller: 'recoverRequestPageController'
            })

            .when('/recoverPasswordPage', {
                templateUrl: 'module/home/recoverPasswordPage/recoverPasswordPage.html',
                controller: 'recoverPasswordPageController'
            })
            
            .otherwise({
                redirectTo: '/'
            });
    });
