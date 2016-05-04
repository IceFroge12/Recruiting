/**
 * Created by dima on 02.05.16.
 */

function appFormController($scope, appFormService) {
    appFormService.loadAppFormData().then(function success(data) {
        $scope.questions = data.questions;
        $scope.user = data.user;
        $scope.status = data.status;
        $scope.data = data;

    }, function error() {
        console.log("error");
    });

    $scope.changeUserName = function () {
        console.log("MDDDDDDDD");
        appFormService.changeUserName($scope.data);
    }
}

angular.module('appStudentForm')
    .controller('appFormController', ['$scope','appFormService', appFormController]);
