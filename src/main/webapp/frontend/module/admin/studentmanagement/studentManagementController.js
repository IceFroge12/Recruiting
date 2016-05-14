/**
 * Created by dima on 29.04.16.
 */

function studentManagementController($scope,$filter, studentManagementService) {

    $scope.pageItems = 9;
    $scope.showFiltration = function () {
        $scope.questions = [];
        $scope.restrictions = [];
        console.log("Finding questions");
        getAllQuestions();
    };
    
    $scope.sort = {
        sortingOrder: 1,
        reverse: false
    };
    $scope.gap = 5;
    $scope.items = [];
    $scope.amount = 0;
    $scope.currentPage = 1;
    $scope.statuses = [];
    $scope.UnivList=[];


    studentManagementService.getAllStatuses().success(function (data) {
        $scope.statuses = data;
        console.log($scope.statuses);
    }, function error() {
        console.log("error with getting allStatus");
    });

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
        console.log(data);
    }, function error() {
        console.log("error");
    });
    
    studentManagementService.getAdvancedCount().success(function(data){
        $scope.approvedToAdvanced = data;
        console.log(data);
    }, function error() {
        console.log("error");
    });
    
    studentManagementService.getGeneralCount().success(function(data){
        $scope.approvedToGeneral = data;
        console.log(data);
    }, function error() {
        console.log("error");
    });
    
    studentManagementService.getJobCount().success(function(data){
        $scope.approvedToWork = data;
        console.log(data);
    }, function error() {
        console.log("error");
    });
    
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


    studentManagementService.showAllStudents(1, $scope.pageItems, $scope.sort.sortingOrder,true).success(function (data) { 
        $scope.allStudents= data;
        console.log("All students " + data)
    }, function error() {
        console.log("error");
    });

    studentManagementService.getCountOfStudents().success(function (data) {
        console.log("Count of students" + data);
        $scope.amount = Math.ceil(data / $scope.pageItems);
    });

    $scope.statusIdArray = [];
    $scope.checkStudentStatus = function (student) {
        $scope.statusIdArray.push(student.appFormId);
        console.log(student.appFormId);
    };

    $scope.statusTemp = [];
    $scope.showAllStudents = function showAllStudents(pageNum) {
        studentManagementService.showAllStudents(pageNum,$scope.pageItems, $scope.sort.sortingOrder,true).success(function (data) {
            $scope.allStudents = data;
            $scope.statusTemp = $scope.allStudents[0].possibleStatus.slice(0);
        }, function error() {
            console.log("error");
        });
    };
    var selectedValue;
    $scope.showSelectStatusValue = function (statusSelect) {
        console.log(statusSelect);
        selectedValue = statusSelect;
    };
    
    $scope.applyStatus = function () {
        console.log("Apply");
        studentManagementService.changeSelectedStatuses(selectedValue, $scope.statusIdArray);
        angular.forEach($scope.statusIdArray,function (item, i) {
           // console.log("ITEMM"+item);
            //console.log(selectedValue);
        });
    };

    $scope.changeStatus = function (status,appFormId) {
        studentManagementService.changeStatus(status,appFormId);
    };

    $scope.showFilteredStudents = function showFilteredStudents(pageNum) {
        studentManagementService.showFilteredStudents(pageNum,$scope.pageItems, $scope.sort.sortingOrder,true, $scope.restrictions).success(function (data) {
            $scope.allStudents = data;
            var list = [];
           checkStatus($scope.allStudents.possibleStatus, $scope.allStudents.status);
            console.log("restrictions:  "+data);
        }, function error() {
            console.log("error");
        });
    };


    $scope.searchStudent = function (studentName) {
        console.log(studentName);
        studentManagementService.searchStudent(studentName,pageNum,$scope.pageItems, $scope.sort.sortingOrder).success(function (data) {
            console.log(data);
            $scope.allStudents = data;
        }, function error() {
            console.log("error");
        });
    };

    $scope.range = function (size, start, end) {
        var ret = [];
        // console.log(size, start, end);


        if (size < end) {
            end = size;
            start = size - $scope.gap;
            end++;
            start++;
        }
        for (var i = start; i < end; i++) {
            if (i > 0)
                ret.push(i);
        }
        return ret;
    };

    $scope.prevPage = function () {
        if ($scope.currentPage > 0) {
            $scope.currentPage--;
        }
    };

    $scope.nextPage = function () {
        if ($scope.currentPage < $scope.pageItems.amount - 1) {
            $scope.currentPage++;
        }
    };

    $scope.setPage = function () {
        $scope.currentPage = this.n;
        $scope.showAllStudents($scope.currentPage);
        //TODO
    };

    $scope.filter= function (){
        $scope.showFilteredStudents($scope.currentPage);
    };
    
}

angular.module('appStudentManagement').$inject = ['$scope', '$filter'];

angular.module('appStudentManagement').directive("customSort", function () {
    return {
        restrict: 'A',
        transclude: true,
        scope: {
            order: '=',
            sort: '='
        },
        template: ' <a ng-click="sort_by(order)" style="color: #555555;">' +
        '    <span ng-transclude></span>' +
        '    <i ng-class="selectedCls(order)"></i>' +
        '</a>',
        link: function (scope) {

            // change sorting order
            scope.sort_by = function (newSortingOrder) {
                var sort = scope.sort;

                if (sort.sortingOrder == newSortingOrder) {
                    sort.reverse = !sort.reverse;
                }

                sort.sortingOrder = newSortingOrder;
            };


            scope.selectedCls = function (column) {
                if (column == scope.sort.sortingOrder) {
                    return (' glyphicon glyphicon-chevron-' + ((scope.sort.reverse) ? 'down' : 'up'));
                }
                else {
                    return 'glyphicon glyphicon-sort'
                }
            };
        }
    }


});


angular.module('appStudentManagement')
    .controller('studentManagementController', ['$scope', '$filter','studentManagementService', studentManagementController]);


