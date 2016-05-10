/**
 * Created by Alona on 06.05.2016.
 */
function appFormStudentController($scope,appFormStudentService,$routeParams) {

    $scope.showAppForm = function showAppForm() {
        var id = $routeParams.id;
            appFormStudentService.getAppForm(id).success(function (data) {
                $scope.appForm = data;
                console.log(data);
            }, function error() {
                console.log("error");
            });
    };

    $scope.showInterview = function showInterview() {
        var appFormId = $scope.appForm.id;
        var role = $scope.role;
        console.log($scope.role);
        appFormStudentService.getInterview(appFormId).success(function (data) {
            $scope.interview = data;
            console.log(data);
        }, function error() {
            console.log("error");
        });
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



angular.module('appStaffAppForm')
    .controller('appFormStudentController', ['$scope','appFormStudentService', '$routeParams', appFormStudentController]);
