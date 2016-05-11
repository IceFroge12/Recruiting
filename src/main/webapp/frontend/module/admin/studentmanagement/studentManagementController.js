/**
 * Created by dima on 29.04.16.
 */

function studentManagementController($scope, studentManagementService) {


    studentManagementService.getRejectCount().success(function(data){
        $scope.rejected = data;
    }, function error() {
        console.log("error");
    });

    studentManagementService.getAdvancedCount().success(function(data){
        $scope.approvedToAdvanced = data;
    }, function error() {
        console.log("error");
    });

    studentManagementService.getGeneralCount().success(function(data){
        $scope.approvedToGeneral = data;
    }, function error() {
        console.log("error");
    });

    studentManagementService.getJobCount().success(function(data){
        $scope.approvedToWork = data;
    }, function error() {
        console.log("error");
    });

    $scope.sort = {
        sortingOrder: 1,
        reverse: false
    };

    $scope.currentPage = 1;
    $scope.status;
    $scope.statuses = [];
    $scope.UnivList=[];

    $scope.$watch("sort.reverse", function () {
        $scope.currentPage = 1;
        $scope.showAllStudents($scope.currentPage);
        console.log($scope.currentPage);
        console.log($scope.sort.reverse);
        console.log($scope.sort.sortingOrder);
    });

    $scope.$watch("sort.sortingOrder", function () {
        $scope.currentPage = 1;
        $scope.showAllStudents($scope.currentPage);
        console.log($scope.currentPage);
        console.log($scope.sort.reverse);
        console.log($scope.sort.sortingOrder);
    });

    studentManagementService.showAllStudents(1, $scope.sort.sortingOrder, $scope.sort.reverse, true).success(function (data) {
        $scope.allStudents = data;
        // angular.forEach(data,function (value, key){
        //     $scope.UnivList.push($scope.getStudentsUniversity(value.id));
        // });console.log($scope.UnivList);
    }, function error() {
        console.log("error");
    });

  

    $scope.showAllStudents = function showAllStudents(pageNum) {
        var itemsByPage = 10;
        studentManagementService.showAllStudents(pageNum, itemsByPage, $scope.sort.sortingOrder, true).success(function (data) {
            $scope.allStudents = data;
            // angular.forEach(data,function (value, key){
            //     $scope.UnivList.push($scope.getUniv(155));
            // });console.log($scope.UnivList);
        }, function error() {
            console.log("error");
        });
    };

    studentManagementService.getStudentsUniversity(155).success(function (data) {
        $scope.university=data.answer+".";
        console.log(data+" university");
    }, function error() {
        console.log("error with getting student university from service");
    });

    // $scope.getUniv = function getUniv(id) {
    //     studentManagementService.getStudentsUniversity(id).success(function (data) {
    //         $scope.university = data.answer + ".";
    //         console.log(data + " university");
    //     }, function error() {
    //         console.log("error with getting student university from service");
    //     });
    // };

    studentManagementService.getStudentsCourse(155).success(function (data) {
        $scope.course = data.answer + ".";
        console.log(data + " course");
    }, function error() {
        console.log("error with getting student course from service");
    });


    studentManagementService.getStudentsStatus(155).success(function (data) {
        $scope.status = data;
        console.log(data + " status");
        if (angular.equals($scope.status.title, "Approved to job")) {
            $scope.approvedToWork += 1;
        } else if (angular.equals($scope.status.title, "Approved to general group")) {
            $scope.approvedToGeneral += 1;
        } else if (angular.equals($scope.status.title, "Rejected")) {
            $scope.rejected += 1;
        } else if (angular.equals($scope.status.title, "Approved to advanced courses")) {
            $scope.approvedToAdvanced += 1;
        }
    }, function error() {
        console.log("error with getting student status from service");
    });

    studentManagementService.getAllStatuses().success(function (data) {
        angular.forEach(data, function (value, key) {
            if (!angular.equals(value.title, $scope.status.title))$scope.statuses.push(value);//$scope.status.title "Approved to general group"
        });
        console.log(data + " allStatus");
    }, function error() {
        console.log("error with getting allStatus");
    });

    $scope.searchStudent = function (studentName) {
        console.log(studentName);
        studentManagementService.searchStudent(studentName).success(function (data) {
            console.log(data);
            $scope.allStudents = data;
        }, function error() {
            console.log("error");
        });
    };

    //studentManagementService.confirmSelection(id, $scope.statusj);
}


angular.module('appStudentManagement')
    .controller('studentManagementController', ['$scope', 'studentManagementService', studentManagementController]);


