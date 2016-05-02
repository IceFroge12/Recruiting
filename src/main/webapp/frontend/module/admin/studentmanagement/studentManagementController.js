/**
 * Created by dima on 29.04.16.
 */
'use strict';
function studentManagementController($scope, studentManagementService,students) {
    $scope.students = students;
    console.log("[" + students+ "]");
    
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


