/**
 * Created by dima on 02.05.16.
 */
'use strict';

function appFormService(http) {
    var service = {};
    service.loadAppFormData = function () {
        return http.post('/student/appform').then(function (response) {
            return response;
        });
    };

    service.changeUserName = function (questions) {
        console.log("MMMMMMMMM");
        http({
            method : 'POST',
            url : '/student/changeUsername',
            contentType: 'application/json',
            data : {
                question: questions
            }
        })
    };

    return service;
}

angular.module('appStudentForm')
    .service('appFormService', ['$http', appFormService]);