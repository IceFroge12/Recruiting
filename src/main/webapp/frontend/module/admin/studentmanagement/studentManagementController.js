/**
 * Created by dima on 29.04.16.
 */
'use strict';
function studentManagementController($scope, studentManagementService) {

    $scope.approvedToWork = 0;
    $scope.approvedToAdvanced = 0;
    $scope.approvedToGeneral = 0;
    $scope.rejected = 0;



    studentManagementService.loadStudents().then(function success(data) {
        $scope.students = (JSON.parse(data));
        // $scope.otherStatus.pop(students.status);
        getStudentsStatus();
    });


    
    function getStudentsStatus() {

        $scope.statuses = ['Approved to Job','Approved to General group',
            'Approved to Advanced group', 'Registered','Approved']; //TODO getAllStatus

        angular.forEach( $scope.students, function (value, key) {
            console.log(value.status);
            if(value.status == "Approved to job"){
                $scope.approvedToWork += 1;
            }
            if(value.status == "Approved to general group"){
                $scope.approvedToGeneral += 1;
            }
            if(value.status == "Rejected"){
                $scope.rejected += 1;
            }
            if(value.status == "Approved to advanced group"){
                $scope.approvedToAdvanced += 1;
            }
        });
    }

    
    // for (var i = 0; i < array.length; i++) {
    //     //var questions = array[i].answers;
    //     //for (var j = 0; j < questions.length; j++)
    //     //   if (questions[j] == "Kurs") {
    //     array[i].kurs = 1;
    //     //   }
    // }

}

angular.module('appStudentManagement')
    .controller('studentManagementController', ['$scope', 'studentManagementService', studentManagementController]);


