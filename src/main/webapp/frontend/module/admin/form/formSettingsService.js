/**
 * Created by dima on 30.04.16.
 */

'use strict';

function formSettingsService(http) {

    var service = {};

    service.getAllQuestionType = function () {
        return http.post('/admin/getAllQuestionType').then(function (response) {
            return response;
        });
    };

    function handleSuccess(response) {
        return ( JSON.parse("[" + response.data + "]") );
    };

    service.getAllQuestion = function (role) {
        var request = http({
            method: "POST",
            url: "/admin/getapplicationquestions",
            params: {
                role: role
            }
        });
        return ( request.then(handleSuccess) );
    };
    
    service.changeQuestionStatus = function (id) {
        var request = http({
            method: "GET",
            url: "/admin/changeQuestionStatus",
            params: {
                id: id
            }
        });
        return ( request.then(handleSuccess) );
    };

    service.changeQuestionMandatoryStatus = function (id) {
        var request = http({
            method: "GET",
            url: "/admin/changeQuestionMandatoryStatus",
            params: {
                id: id
            }
        });
        return ( request.then(handleSuccess) );
    };
    
    service.addQuestion = function (question, type, enable,mandatory, formAnswerVariants, role) {
        http({
            method: 'POST',
            url: '/admin/addAppFormQuestion',
            contentType: 'application/json',
            data: JSON.stringify({
                question: question,
                type: type,
                enable: enable,
                mandatory: mandatory,
                formAnswerVariants: formAnswerVariants,
                role: role
            })
        });
    };

    service.editQuestion = function (id, question, type, enable, formAnswerVariants, role) {
        http({
            method: 'POST',
            url: '/admin/updateAppFormQuestion',
            contentType: 'application/json',
            data: JSON.stringify({
                id:id,
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