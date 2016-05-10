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

    service.getAppForm = function () {
        console.log("Service getAppForm");
        return http({
            method : 'POST',
            url : '/staff/getApplicationForm'+101
            //params : {appFormId:appFormId}
        })

    };
    service.getInterview = function (appFormId) {
        console.log("Service getInterview");
        return http({
            method : 'POST',
            url : '/staff/getInterview'+appFormId
           // params : {applicationFormId:42}
        })

    };

    return service;
}

angular.module('appStaffAppForm')
    .service('appFormStudentService', ['$http', appFormStudentService]);