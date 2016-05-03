/**
 * Created by dima on 30.04.16.
 */

function formAppController($scope, formAppService) {
    formAppService.getAllQuestion().then(function success(data) {
        console.log(data);
    });
}

angular.module('appAdminForm')
    .controller('formAppController', ['$scope', 'formAppService', formAppController]);