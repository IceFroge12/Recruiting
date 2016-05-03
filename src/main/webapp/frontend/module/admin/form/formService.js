/**
 * Created by dima on 30.04.16.
 */

'use strict';

function formAppService(http) {

    var service = {};

    service.getAllQuestion= function () {
        return http.post('/admin/getapplicationquestions').then(function (response) {
            console.log("["+response+"]");
            return response.data;
        });
    };
    
    return service;
}

angular.module('appAdminForm')
    .service('formAppService', ['$http', formAppService])