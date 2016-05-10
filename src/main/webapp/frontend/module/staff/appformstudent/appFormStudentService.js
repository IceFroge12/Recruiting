/**
 * Created by Alona on 06.05.2016.
 */
'use strict';

function appFormStudentService(http) {
    var service = {};
    service.loadAppFormData = function () {
        return http.post('/staff/appform').then(function (response) {
            return response.data;
        });
    };

    service.getAppForm = function (id) {
        console.log("Service getAppForm");
        return http({
            method: 'POST',
            url: '/staff/getApplicationForm/' + id
        })
    };
    service.getInterview = function (appFormId, role) {
        console.log("Service getInterview");
        return http({
            method: 'POST',
            url: '/staff/getInterview/' + appFormId,
            params: {role: role}
        })

    };

    return service;
}

angular.module('appStaffAppForm')
    .service('appFormStudentService', ['$http', appFormStudentService]);