/**
 * Created by dima on 29.04.16.
 */
'use strict';

function studentManagementService(http) {
    var service = {};

    service.loadStudents = function () {
        return http.post('/admin/getallstudent').then(function (response) {
            return  response;
        });
    };

    return service;
}

angular.module('appStudentManagement')
    .service('studentManagementService', ['$http', studentManagementService]);