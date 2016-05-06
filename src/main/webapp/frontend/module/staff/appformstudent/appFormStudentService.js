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

    service.changeUserName = function (data) {
        console.log(data);
        http({
            method : 'POST',
            url : '/staff/saveApplicationForm',
            contentType: 'application/json',
            data : {
                id : data.id,
                status : data.status,
                user : data.user,
                questions : data.questions
            }
        })
    };

    return service;
}

angular.module('appStaffAppForm')
    .service('appFormStudentService', ['$http', appFormStudentService]);