/**
 * Created by Alona on 06.05.2016.
 */
function appFormStudentController($scope,$http, appFormStudentService,$routeParams) {

    $scope.showAppForm = function showAppForm() {
        var id = $routeParams.id;
            appFormStudentService.getAppForm(id).success(function (data) {
                $scope.appForm = data;
                console.log(data);
                var appFormId = $scope.appForm.id;
                appFormStudentService.getRoles(appFormId).success(function (data) {
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
        appFormStudentService.getInterview(appFormId,role).success(function (data) {
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

    $scope.submitInterview = function submitInter(interviewTech) {
        appFormStudentService.submitInterview(interviewTech).success(function (data) {
            console.log(data);

        }, function error() {
            console.log("error");
        });
    };
    $scope.submitInter = function (data) {
        var req =  $http({
            method : 'POST',
            url : '/staff/submitInterview',
            contentType: 'application/json',
            data : {
                adequateMark: data.adequateMark,
                applicationForm: data.applicationForm,
                date: data.date,
                id: data.id,
                interviewer: data.interviewer,
                mark: data.mark,
                questions: data.questions,
                role: data.role
            }
        });
        var response;
        req.success(function(data) {
            response =  data;
        });
        return response;
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
    .controller('appFormStudentController', ['$scope','$http','appFormStudentService', '$routeParams', appFormStudentController]);
