/**
 * Created by dima on 29.04.16.
 */
'use strict';

function studentManagementService(http) {

    var service = {};

    service.showAllStudents = function (pageNum, rowsNum, sortingCol, increase) {
        console.log("Service showAllStudents");
        return http({
            method: 'GET',
            url: '/admin/showAllStudents',
            params: {pageNum: pageNum, rowsNum: rowsNum, sortingCol: sortingCol, increase: increase}
        })
    };

    service.changeSelectedStatuses = function (changeStatus, appFormIdList) {
        console.log("STATUSE"+changeStatus+appFormIdList);
        return http({
            method: 'POST',
            url: '/admin/changeSelectedStatuses',
            params: {
                changeStatus: changeStatus,
                appFormIdList: appFormIdList
            }
        })
    };

    service.changeStatus = function (changeStatus, appFormId) {
        return http({
            method: 'POST',
            url: '/admin/changeStatus',
            params: {
                changeStatus: changeStatus,
                appFormId: appFormId
            }
        })
    };

    service.getCountOfStudents = function () {
        return http({
            method: 'GET',
            url: '/admin/getCountOfStudents'
        })
    };

    service.getRejectCount = function () {
        return http({
            method: 'GET',
            url: '/admin/getRejectCount'
        })
    };

    service.getJobCount = function () {
        return http({
            method: 'GET',
            url: '/admin/getJobCount'
        })
    };

    service.getAdvancedCount = function () {
        return http({
            method: 'GET',
            url: '/admin/getAdvancedCount'
        })
    };

    service.getGeneralCount = function () {
        return http({
            method: 'GET',
            url: '/admin/getGeneralCount'
        })
    };

    service.confirmSelection = function (id, status) {
        return http({
            method: 'POST',
            url: '/admin/confirmSelection',
            params: {id: id},
            data: status
        })
    };

    service.searchStudent = function (lastName) {
        return http({
            method: 'POST',
            url: '/admin/searchStudent',
            params: {
                lastName: lastName,
                pageNum: pageNum,
                rowsNum: rowsNum,
                sortingCol: sortingCol
            }
        })
    };

    service.getAllQuestions = function () {
        var request = http({
            method: "POST",
            url: "/admin/getapplicationquestionsnontext",
            params: {
                role: "ROLE_STUDENT"
            }
        });
        return ( request.then(handleSuccess) );
    };

    service.showFilteredStudents = function (pageNum, rowsNum, sortingCol, increase, restrictions) {
        console.log("Service showFilteredStudents");
        return http({
            method: 'GET',
            url: '/admin/showFilteredStudents',
            params: {pageNum: pageNum, rowsNum: rowsNum, sortingCol: sortingCol, increase: increase, restrictions: restrictions}
        })
    };

    function handleSuccess(response) {
        return ( JSON.parse("[" + response.data + "]") );
    }

    return service;
}

angular.module('appStudentManagement')
    .service('studentManagementService', ['$http', studentManagementService]);