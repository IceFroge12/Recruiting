/**
 * Created by dima on 02.05.16.
 */

function appFormController($scope, appFormService) {
    appFormService.loadAppFormData().then(function success(data) {
        $scope.questions = data.data.questions;
        $scope.user = data.data.user;
        $scope.status = data.data.status;

    }, function error() {
        console.log("error");
    });

    $scope.changeUserName = function () {
        console.log("MDDDDDDDD");
        appFormService.changeUserName($scope.questions);
    }
}

angular.module('appStudentForm')
    .controller('appFormController', ['$scope','appFormService', appFormController]);
