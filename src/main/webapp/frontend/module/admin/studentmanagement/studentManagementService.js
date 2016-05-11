/**
 * Created by dima on 29.04.16.
 */
'use strict';

function studentManagementService(http) {

    var service = {};

    service.showAllStudents = function (pageNum, rowsNum, sortingCol, increase) {
        console.log("Service showAllStudents");
        return http({
            method : 'GET',
            url : '/admin/showAllStudents',
            params : {pageNum:pageNum, rowsNum: rowsNum, sortingCol:sortingCol, increase: increase}
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

    service.getRejectCount = function () {
        return http({
            method : 'GET',
            url : '/admin/getRejectCount'
        })
    };

    service.getJobCount = function () {
        return http({
            method : 'GET',
            url : '/admin/getJobCount'
        })
    };

    service.getAdvancedCount = function () {
        return http({
            method : 'GET',
            url : '/admin/getAdvancedCount'
        })
    };

    service.getGeneralCount = function () {
        return http({
            method : 'GET',
            url : '/admin/getGeneralCount'
        })
    };
    
    service.confirmSelection = function (id, status) {
        return http({
            method : 'POST',
            url : '/admin/confirmSelection',
            params:{id:id},
            data: status
        })
    };


    return service;
}

angular.module('appStudentManagement')
    .service('studentManagementService', ['$http', studentManagementService]);