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
        // angular.forEach( $scope.allStudents, function (value, key) {
        //     console.log(value.status);
        //     if(value.status == "Approved to Job"){
        //         $scope.approvedToWork += 1;
        //     }
        //     if(value.status == "Approved to General Courses"){
        //         $scope.approvedToGeneral += 1;
        //     }
        //     if(value.status == "Rejected"){
        //         $scope.rejected += 1;
        //     }
        //     if(value.status == "Approved to Advanced Coursec"){
        //         $scope.approvedToAdvanced += 1;
        //     }
        // });
    }, function error() {
        console.log("error");
    });

    // studentManagementService.getStudentsUniversity(221).success(function (data) {
    //     $scope.university=data;
    // }, function error() {
    //     console.log("error with getting student university from service");
    // });
    
    // studentManagementService.getStudentsCourse(221).success(function (data) {
    //     $scope.course=data;
    // }, function error() {
    //     console.log("error with getting student course from service");
    // });

    studentManagementService.getStudentsStatus(221).success(function (data) {
        $scope.status=data;
    }, function error() {
        console.log("error with getting Employee roles from service");
    });
    
    
    
    $scope.statuses =
        [{studentStatus: 'Registered'},
            {studentStatus: 'In review'},
            {studentStatus: 'Approved'},
            {studentStatus: 'Rejected'},
            {studentStatus: 'Pending result'},
            {studentStatus: 'Approved to job'},
            {studentStatus: 'Approved to general group'},
            {studentStatus: 'Approved to advanced courses'}
        ];
    

  }

angular.module('appStudentManagement')
    .controller('studentManagementController', ['$scope', 'studentManagementService', studentManagementController]);


