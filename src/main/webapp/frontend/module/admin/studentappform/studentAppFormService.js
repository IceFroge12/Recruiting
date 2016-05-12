/**
 * Created by Admin on 12.05.2016.
 */
'use strict';

function appFormStudentService(http) {

    var service = {};

    service.getAppForm = function (id) {
        console.log("Service getAppForm");
        return http({
            method: 'POST',
            url: '/staff/getApplicationForm/' + id
        })
    };
    
    return service;
}

angular.module('appFormStudent')
    .service('appFormStudentService', ['$http', appFormStudentService]);