/**
 * Created by dima on 29.04.16.
 */
'use strict';

function studentManagementService(http) {

    var service = {};

    service.showAllStudents = function (pageNum) {
        return http({
            method : 'GET',
            url : '/admin/showAllStudents',
            params : {pageNum:pageNum}
        })

    };

    service.getAllStatuses = function () {
        return http({
            method : 'GET',
            url : '/admin/getAllStatuses'
        })
    };
    
    service.getStudentsUniversity = function (id) {
        return http({
            method : 'GET',
            url : '/admin/getUniverse',
            params:{id:id}
        })
    };

    service.getStudentsCourse = function (id) {
        return http({
            method : 'GET',
            url : '/admin/getCourse',
            params:{id:id}
        })
    };

    service.getStudentsStatus = function (id) {
        return http({
            method : 'GET',
            url : '/admin/getStatus',
            params:{id:id}
        })
    };
    
    
    return service;
}

angular.module('appStudentManagement')
    .service('studentManagementService', ['$http', studentManagementService]);