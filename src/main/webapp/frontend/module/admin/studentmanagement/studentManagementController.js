/**
 * Created by dima on 29.04.16.
 */

function studentManagementController($scope, studentManagementService) {

    $scope.approvedToWork = 0;
    $scope.approvedToAdvanced = 0;
    $scope.approvedToGeneral = 0;
    $scope.rejected = 0;

    $scope.sort = {
        sortingOrder: 1,
        reverse: false
    };

    $scope.currentPage = 1;


    $scope.$watch("sort.reverse",function(){
        $scope.currentPage = 1;
        $scope.showAllStudents($scope.currentPage);
        console.log($scope.currentPage);
        console.log($scope.sort.reverse);
        console.log($scope.sort.sortingOrder);
    });

    $scope.$watch("sort.sortingOrder",function(){
        $scope.currentPage = 1;
        $scope.showAllStudents($scope.currentPage);
        console.log($scope.currentPage);
        console.log($scope.sort.reverse);
        console.log($scope.sort.sortingOrder);
    });

    studentManagementService.showAllStudents(1, $scope.sort.sortingOrder, $scope.sort.reverse, true).success(function (data) {
        $scope.allStudents = data;
    }, function error() {
        console.log("error");
    });

    $scope.showAllStudents = function showAllStudents(pageNum) {
        var itemsByPage = 10;
        studentManagementService.showAllStudents(pageNum,itemsByPage, $scope.sort.sortingOrder,true).success(function (data) {
            $scope.allStudents = data;
            console.log(data);
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

    studentManagementService.getStudentsCourse(155).success(function (data) {
        $scope.course=data.answer+".";
        console.log(data+" course");
    }, function error() {
        console.log("error with getting student course from service");
    });

    //$scope.status={id:null, title:''};
    studentManagementService.getStudentsStatus(155).success(function (data) {
        $scope.status=data;
        console.log(data+" status");
        if(angular.equals($scope.status.title,"Approved to job")){
            $scope.approvedToWork += 1;
        }else
        if(angular.equals($scope.status.title, "Approved to general group")){
            $scope.approvedToGeneral += 1;
        }else
        if(angular.equals($scope.status.title, "Rejected")){
            $scope.rejected += 1;
        }else
        if(angular.equals($scope.status.title, "Approved to advanced courses")){
            $scope.approvedToAdvanced += 1;
        }
    }, function error() {
        console.log("error with getting student status from service");
    });

    studentManagementService.getAllStatuses().success(function(data){

        $scope.statuses=[];
        angular.forEach(data, function (value, key){
            if(!angular.equals(value.title,"Approved to general group"))$scope.statuses.push(value);//$scope.status.title
        });
        console.log(data+" allStatus");
    }, function error() {
        console.log("error with getting allStatus");
    });

    //studentManagementService.confirmSelection(id, $scope.statusj);
  }



angular.module('appStudentManagement')
    .controller('studentManagementController', ['$scope', 'studentManagementService', studentManagementController]);


