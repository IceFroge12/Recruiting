/**
 * Created by dima on 30.04.16.
 */
'use strict';

function staffManagementService(http) {

    var service = {};
    
    service.showAllEmployees = function () {
        return http.post('/admin/showAllEmployee').then(function (response) {
            console.log(response.data);
            return response.data;
        });
    };
    
    return service;
}

angular.module('appStaffManagement')
    .service('staffManagementService', ['$http', staffManagementService]);