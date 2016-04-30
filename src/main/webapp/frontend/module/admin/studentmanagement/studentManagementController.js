/**
 * Created by dima on 29.04.16.
 */
(function () {
    'use strict';
    function studentManagementController($scope, studentManagementService) {

        var array = [{
            "ID": 2,
            "status": "Approved to Job",
            "active": true,
            "recruitment": {
                "name": "2015"
            },
            "photoScope": "hghdg",
            "user": {
                "firstName": "dasdfasf",
                "lastName": "asdfasdf",
                "email": "life__is_good@ukr.net"
            },
            "answers": []
        },
            {
                "ID": 1,
                "status": "Approved to General Courses",
                "active": true,
                "recruitment": {
                    "name": "dima"
                },
                "photoScope": "sdf",
                "user": {
                    "firstName": "sdfsdf",
                    "lastName": "sdfdsf",
                    "email": "georgius12@gmail.com"
                },
                "answers": [
                    {
                        "question": "Kurs",
                        "answer": "1"
                    },
                    {
                        "question": "Who are you?",
                        "answer": "I am a test"
                    },
                    {
                        "question": "Your University",
                        "answer": "KPI"
                    }
                ]
            }];

        studentManagementService.loadStudents().then(function success(data) {
            console.log(data);
            $scope.students = data;
        }, function error() {
            console.log("error");
        });


        for (var i = 0; i < array.length; i++) {
            //var questions = array[i].answers;
            //for (var j = 0; j < questions.length; j++)
            //   if (questions[j] == "Kurs") {
            array[i].kurs = 1;
            //   }
        }
        console.log(array);

        $scope.students = array;


    }


    angular.module('appStudentManagement')
        .controller('studentManagementController', ['$scope', 'studentManagementService', studentManagementController]);
})();

