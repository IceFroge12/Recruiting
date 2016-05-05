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
    };

    $scope.toggle = function (item, list){
        var idx=-1;
        for(var i=0; i<list.length; i++){
            if(list[i].answer==item)
                idx=i;
        }
        if (idx >-1){
            list.splice(idx,1);
        }
        else {
            list.push({answer: item});
        }
        console.log(list);
    };

    $scope.exists = function (item, list){
        for(var i=0; i<list.length; i++){
            if(list[i].answer==item)
                return true;
        }
        return false;
    };
}

angular.module('appStudentForm')
    .controller('appFormController', ['$scope','appFormService', appFormController]);
