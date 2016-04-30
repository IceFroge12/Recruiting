/**
 * Created by dima on 30.04.16.
 */
'use strict';

function staffManagementService(http) {
    var service = {};
    
    // service.loadStudents = function () {
    //     return http.post('/admin/getallstudent').then(function (response) {
    //         return JSON.parse("["+response.data+"]");
    //     });
    // };

    return service;
}

angular.module('appStaffManagement')
    .service('staffManagementService', ['$http', staffManagementService])