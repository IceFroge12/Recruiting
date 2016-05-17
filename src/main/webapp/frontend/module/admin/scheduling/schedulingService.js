/**
 * Created by dima on 30.04.16.
 */
'use strict';

function schedulingService(http) {

    var service = {};
    service.getCurrentRecruitmentCountStudents = function () {
        return http.get('/scheduling/getStudentCount').then(function (response) {
            console.log(response.data);
            return response.data;
        });
    };

    service.getCurrentSchedule = function () {
        console
        return http({
            method : 'GET',
            url : '/scheduling/getCurrentSchedule'
        })
    };

    return service;
}

angular.module('appScheduling')
    .service('schedulingService', ['$http', schedulingService]);