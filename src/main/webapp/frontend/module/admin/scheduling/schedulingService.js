/**
 * Created by dima on 30.04.16.
 */
'use strict';

function schedulingService(http) {

    var service = {};
    service.getCurrentRecruitmentCountStudents = function () {
        return http.post('/scheduling/getStudentCount').then(function (response) {
            console.log(response.data);
            return response.data;
        });
    };
    return service;
}

angular.module('appScheduling')
    .service('schedulingService', ['$http', schedulingService]);