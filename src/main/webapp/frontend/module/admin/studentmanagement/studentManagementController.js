/**
 * Created by dima on 29.04.16.
 */

function studentManagementController($scope, studentManagementService) {

    $scope.showFiltration = function () {
        $scope.questions = [];
        $scope.restrictions = [];
        console.log("Finding questions");
        getAllQuestions();
    };

    function getAllQuestions() {
        studentManagementService.getAllQuestions().then(function success(data) {
            for (var i = 0, len = data.length; i < len; i++) {
                if (typeof data[i].variants !== "undefined"
                    && data[i].variants !== "undefined") {
                    data[i].variants = JSON.parse(data[i].variants);
                    $scope.questions = data;
                }
            }
            $scope.restrictions = angular.copy($scope.questions);
            console.log($scope.restrictions);
            console.log($scope.questions);
        });
    }

    $scope.toggle = function (variant, question) {
        var x = 0;
        for (var k = 0; k < $scope.restrictions.length; k++) {
            if ($scope.restrictions[k].id == question.id) {
                x=k;
            }
        }
        var idx = -1;
        for (var i = 0; i < $scope.restrictions[x].variants.length; i++) {
            if ($scope.restrictions[x].variants[i].variant == variant.variant)
                idx = i;
        }
        if (idx > -1) {
            $scope.restrictions[x].variants.splice(idx, 1);
        }
        else {
            $scope.restrictions[x].variants.push({variant: variant});
        }
        console.log($scope.restrictions);
        console.log($scope.questions);
    };

    $scope.exists = function (variant, question) {
        for (var i = 0; i < $scope.restrictions.length; i++) {
            if ($scope.restrictions[i].id == question.id) {
                for (var j = 0; j < $scope.restrictions[i].variants.length; j++)
                    if ($scope.restrictions[i].variants[j].variant == variant.variant)
                        return true;
            }
        }
        return false;
    };

    studentManagementService.getRejectCount().success(function (data) {
        $scope.rejected = data;
    }, function error() {
        console.log("error");
    });

    studentManagementService.getAdvancedCount().success(function (data) {
        $scope.approvedToAdvanced = data;
    }, function error() {
        console.log("error");
    });

    studentManagementService.getGeneralCount().success(function (data) {
        $scope.approvedToGeneral = data;
    }, function error() {
        console.log("error");
    });

    studentManagementService.getJobCount().success(function (data) {
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
    $scope.UnivList = [];

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
        $scope.university = data.answer + ".";
        console.log(data + " university");
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

    //studentManagementService.confirmSelection(id, $scope.statusj);
}


angular.module('appStudentManagement')
    .controller('studentManagementController', ['$scope', 'studentManagementService', studentManagementController]);


