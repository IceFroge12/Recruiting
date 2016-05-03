/**
 * Created by dima on 30.04.16.
 */

'use strict';

function formAppService(http) {

    var service = {};

    service.getAllQuestion= function () {
        return http.post('/admin/getapplicationquestions').then(function (response) {
            return JSON.parse("["+response.data+"]");
        });
    };
    
    return service;
}

angular.module('appAdminForm')
    .service('formAppService', ['$http', formAppService])