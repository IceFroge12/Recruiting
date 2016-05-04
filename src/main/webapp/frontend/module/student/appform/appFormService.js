/**
 * Created by dima on 02.05.16.
 */
'use strict';

function appFormService(http) {
    var service = {};
    service.loadAppFormData = function () {
        return http.post('/student/appform').then(function (response) {
            return response.data;
        });
    };

    service.changeUserName = function (data) {
        console.log("MMMMMMMMM");
        http({
            method : 'POST',
            url : '/student/saveApplicationForm',
            contentType: 'application/json',
            data : {
                ID : data.ID,
                status : data.status,
                user : data.user,
                questions : data.questions
            }
        })
    };

    return service;
}

angular.module('appStudentForm')
    .service('appFormService', ['$http', appFormService]);