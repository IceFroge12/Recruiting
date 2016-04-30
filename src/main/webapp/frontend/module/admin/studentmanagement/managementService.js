/**
 * Created by dima on 29.04.16.
 */
(function () {
    'use strict';

    function studentManagementService(http) {
        var service = {};

        service.loadStudents = function () {
            return http.post('/admin/getallstudent').then(function (response) {
                return JSON.parse("["+response.data+"]");
            });
        };
        
        return service;
    }

    angular.module('appStudentManagement')
        .service('studentManagementService', ['$http', studentManagementService])
})();