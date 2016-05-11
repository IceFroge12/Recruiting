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

            appFormStudentService.getRoles().success(function (data) {
                $scope.roles = data;
                console.log(data);
                $scope.roleTech = false;
                $scope.roleSoft = false;
                for(var i=0; i< data.length; i++){

                    if(data[i].id==2){
                        $scope.roleTech = true;
                    }

                    if(data[i].id==5){
                        $scope.roleSoft = true;
                    }
                }
                console.log($scope.roleTech);
                console.log($scope.roleSoft);
            }, function error() {
                console.log("error");
            });

    };

    $scope.showInterview = function showInterview(role) {
        var appFormId = $scope.appForm.id;
        appFormStudentService.getInterview(appFormId,role).success(function (data) {
            var role = data.role;
            console.log(role);
            if(role == 2) {
                $scope.interviewTech = data;

            }else if (role == 5){
                $scope.interviewSoft = data;
            }
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
