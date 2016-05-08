/**
 * Created by dima on 30.04.16.
 */

'use strict';

function formSettingsService(http) {

    var service = {};

    function handleSuccess( response ) {
        return( JSON.parse("["+response.data+"]") );
    }

    service.getAllQuestion = function(role) {
        var request = http({
            method: "post",
            url: "/admin/getapplicationquestions",
            params: {
                role: role
            }
        });
        return( request.then(handleSuccess) );
    };

  

    service.addQuestion = function (question, type, enable, formAnswerVariants,role) {
        http({
            method : 'POST',
            url : '/admin/addAppFormQuestion',
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
    .service('formSettingsService', ['$http', formSettingsService]);