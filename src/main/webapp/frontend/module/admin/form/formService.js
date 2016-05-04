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

    service.addQuestion = function (question, type, enable, formAnswerVariants,role) {
        http({
            method : 'POST',
            url : '/admin/addappformquestion',
            contentType: 'application/json',
            data : JSON.stringify({
                question: question,
                type: type,
                enable: enable,
                formAnswerVariants: formAnswerVariants,
                role: role
            })
        });
    };
    
    return service;
}

angular.module('appAdminForm')
    .service('formAppService', ['$http', formAppService])