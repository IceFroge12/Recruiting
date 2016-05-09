/**
 * Created by dima on 29.04.16.
 */
'use strict';
function studentManagementController($scope, studentManagementService) {

    $scope.approvedToWork = 0;
    $scope.approvedToAdvanced = 0;
    $scope.approvedToGeneral = 0;
    $scope.rejected = 0;



    studentManagementService.showAllStudents(1).success(function (data) {
        $scope.allStudents = data;
    }, function error() {
        console.log("error");
    });

    studentManagementService.getStudentsUniversity(74).success(function (data) {
        $scope.university=data.answer+".";
        console.log(data+" university");
    }, function error() {
        console.log("error with getting student university from service");
    });
    
    studentManagementService.getStudentsCourse(74).success(function (data) {
        $scope.course=data.answer+".";
        console.log(data+" course");
    }, function error() {
        console.log("error with getting student course from service");
    });

    studentManagementService.getStudentsStatus(74).success(function (data) {
        $scope.status=data;
        console.log(data+" status");
        if(angular.equals($scope.status.title,"Registered")){
            $scope.approvedToWork += 1;
        }else
        if(angular.equals($scope.status.title, "Approved to General Courses")){
            $scope.approvedToGeneral += 1;
        }else
        if(angular.equals($scope.status.title, "Rejected")){
            $scope.rejected += 1;
        }else
        if(angular.equals($scope.status.title, "Approved to Advanced Coursec")){
            $scope.approvedToAdvanced += 1;
        }
    }, function error() {
        console.log("error with getting student status from service");
    });
    
    studentManagementService.getAllStatuses().success(function(data){
        $scope.statuses=data;
        //$scope.statuses=[];
        // angular.forEach(data, function (value, key){
        //     if(!angular.equals(value.title,scope.status.title))$scope.statuses.add(value);
        // })
        console.log(data+" allStatus");
    }, function error() {
        console.log("error with getting allStatus");
    });

  }

angular.module('appStudentManagement')
    .controller('studentManagementController', ['$scope', 'studentManagementService', studentManagementController]);


