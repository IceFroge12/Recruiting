/**
 * Created by Admin on 12.05.2016.
 */
'use strict';

function studentAppFormService(http) {

    var service = {};

    service.getAppForm = function (id) {
        console.log("Service getAppForm");
        return http({
            method: 'POST',
            url: '/admin/getApplicationForm/' + id
        })
    };

    service.getRolesInt = function (appFormId) {
        console.log("Service getRoles");
        return http({
            method: 'GET',
            url: '/staff/getRolesInterview/'+ appFormId
        })
    };
    service.getInterview = function (appFormId, role) {
        console.log("Service getInterview");
        return http({
            method: 'POST',
            url: '/staff/getInterview/' + appFormId + '/' + role
        })
    };
    return service;
}

angular.module('appFormStudents')
    .service('studentAppFormService', ['$http', studentAppFormService]);