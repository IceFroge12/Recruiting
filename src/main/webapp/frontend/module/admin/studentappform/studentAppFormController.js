/**
 * Created by Admin on 12.05.2016.
 */
function adminStudentFormController($scope, studentAppFormService, $routeParams) {

    $scope.showAppForm = function showAppForm() {
        var id = $routeParams.id;
        studentAppFormService.getAppForm(id).success(function (data) {
            $scope.appForm = data;
            console.log(data);
            var appFormId = $scope.appForm.id;
            studentAppFormService.getRolesInt(appFormId).success(function (data) {
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
        }, function error() {
            console.log("error");
        });
    };


    $scope.showInterview = function showInterview(role) {
        var appFormId = $scope.appForm.id;
        studentAppFormService.getInterview(appFormId,role).success(function (data) {
            console.log(data);
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
    $scope.exists = function (item, list){
        for(var i=0; i<list.length; i++){
            if(list[i].answer==item)
                return true;
        }
        return false;
    };
}

angular.module('appFormStudents')
    .controller('adminStudentFormController', ['$scope','studentAppFormService', '$routeParams',adminStudentFormController]);