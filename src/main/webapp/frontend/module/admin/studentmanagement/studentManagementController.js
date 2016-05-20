/**
 * Created by dima on 29.04.16.
 */

function studentManagementController($scope,$filter, studentManagementService) {
    $scope.sort = {
        sortingOrder: 1,
        reverse: false
    };
    $scope.gap = 5;
    $scope.items = [];
    $scope.amount = 0;
    $scope.currentPage = 1;
    $scope.itemsPerPage = 15;
    $scope.statuses = [];
    $scope.UnivList=[];
    $scope.filtered = false;
    $scope.checkedAll = false;
    $scope.isActive = true;

    $scope.showFiltration = function () {
        $scope.questions = [];
        $scope.restrictions = [];
        $scope.statusesChoosen = [];
        console.log($scope.statusesChoosen);
        console.log("Finding questions");
        getAllQuestions();
        console.log($scope.restrictions);
        console.log($scope.questions);
    };

    $scope.calculateStatuses = function() {
        studentManagementService.calculateStatuses().success(function(data) {
            console.log('Confirm selection');
            console.log(data);
        })
    };
    

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
            //for(var i=0; i<$scope.restrictions.length; i++){
            //    for(var j=0; j<$scope.restrictions.variants[i].length(); j++){
            //        $scope.restrictions.variants[i];
            //    }
            //}
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

    $scope.toggleItem = function (item, list) {
        var idx=-1;
        for(var i = 0; i<list.length; i++){
            if(list[i].title == item.title)
            idx=i;
        }
        console.log("idx="+idx);
        if (idx > -1) {
            list.splice(idx, 1);
        }
        else {
            list.push(item);
        }
        console.log($scope.statusTemp);
        console.log($scope.statusesChoosen);
    };

    $scope.existsItem = function (item, list){
        for(var i=0; i<list.length; i++){
            if(list[i].title==item.title)
                return true;
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
        if ($scope.filtered)
            $scope.showFilteredStudents($scope.currentPage);
        else
            $scope.showAllStudents($scope.currentPage);

    });
    
    $scope.$watch("sort.sortingOrder", function () {
        $scope.currentPage = 1;
        if ($scope.filtered)
            $scope.showFilteredStudents($scope.currentPage);
        else
            $scope.showAllStudents($scope.currentPage);

    });

    $scope.$watch("itemsPerPage", function () {
        $scope.currentPage = 1;
        $scope.getCountOfStudents();
        if ($scope.filtered)
            $scope.showFilteredStudents($scope.currentPage);
        else
            $scope.showAllStudents($scope.currentPage);
    });

    studentManagementService.showAllStudents(1, $scope.itemsPerPage, $scope.sort.sortingOrder,$scope.sort.reverse).success(function (data) {
        $scope.allStudents= data;
        console.log("All students " + data);
        angular.forEach($scope.allStudents,function (item, i) {
            // console.log("ITEMM"+item.status);
            //console.log(selectedValue);
        });
    }, function error() {
        console.log("error");
    });

    studentManagementService.getCountOfStudents().success(function (data) {
        console.log("Count of students" + data);
        $scope.amount = Math.ceil(data / $scope.itemsPerPage);
    });

    $scope.getCountOfStudents = function(){
        studentManagementService.getCountOfStudents().success(function (data) {
            console.log("Count of students" + data);
            $scope.amount = Math.ceil(data / $scope.itemsPerPage);
        });
    };

    $scope.statusIdArray = [];
    $scope.checkStudentStatus = function (student) {
        var flag = false;
        if($scope.statusIdArray.length != 0){
        angular.forEach($scope.statusIdArray, function (item, i) {
            if(item == student.appFormId){
                $scope.statusIdArray.splice(i,1);
                flag = true;
            }
        });
        if (flag == false) {
            $scope.statusIdArray.push(student.appFormId);
        }
        }
        if($scope.statusIdArray.length == 0){
            $scope.statusIdArray.push(student.appFormId);
        }
        console.log(student.appFormId);
        console.log($scope.statusIdArray);
    };

    $scope.selectAll = function() {
        var statusIdArray = [];
        if ($scope.checkedAll == false) {
            angular.forEach($scope.allStudents, function (item, i) {
                statusIdArray.push(item.appFormId);
            });
            $scope.statusIdArray = statusIdArray;
            $scope.checkedAll = true;
        }
        else if ($scope.checkedAll == true){
            $scope.checkedAll = false;
            $scope.statusIdArray = [];
            console.log($scope.checkedAll);
            console.log($scope.statusIdArray);
        }
    };

    $scope.statusTemp = [];
    $scope.showAllStudents = function showAllStudents(pageNum) {
        studentManagementService.showAllStudents(pageNum,$scope.itemsPerPage, $scope.sort.sortingOrder,$scope.sort.reverse).success(function (data) {
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
        studentManagementService.showFilteredStudents(pageNum,$scope.itemsPerPage, $scope.sort.sortingOrder,
            $scope.sort.reverse, $scope.restrictions, $scope.statusesTitle, $scope.isActive).success(function (data) {
            $scope.allStudents = data;
            var list = [];
            //checkStatus($scope.allStudents.possibleStatus, $scope.allStudents.status);
            console.log("restrictions:  "+data);
        }, function error() {
            console.log("error");
        });
    };


    $scope.searchStudent = function (studentName) {
        console.log(studentName);
        var pageNum = 1;
        studentManagementService.searchStudent(studentName,pageNum,$scope.itemsPerPage).success(function (data) {
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
        if ($scope.currentPage < $scope.itemsPerPage.amount - 1) {
            $scope.currentPage++;
        }
    };

    $scope.setPage = function () {
        $scope.currentPage = this.n;
        if ($scope.filtered)
            $scope.showFilteredStudents($scope.currentPage);
        else
            $scope.showAllStudents($scope.currentPage);
    };

    $scope.filter= function (){
        $scope.filtered = true;
        $scope.currentPage = 1;
        $scope.statusesTitle = [];
        angular.forEach($scope.statusesChoosen,function (item, i) {
            $scope.statusesTitle.push(item.title);
        });
        console.log($scope.statusesTitle);
        $scope.showFilteredStudents($scope.currentPage);
    };
    
    
    $scope.confirmSelectionInfo = function() {
    	studentManagementService.getApprovedCount().success(function(data){
            $scope.approved = data;
            console.log(data);
        }, function error() {
            console.log("error");
        });
    	
    	studentManagementService.getTimePoints().success(function(data){
            $scope.timePoints = data;
            console.log(data);
        }, function error() {
            console.log("error");
        });
    };
    
    $scope.confirmSelection = function() {
    	studentManagementService.confirmSelection().success(function(data) {
    		console.log('Confirm selection');
    		console.log(data);
    	})
    };
    
    $scope.announceResults = function() {
    	studentManagementService.announceResults().success(function(data) {
    		console.log('Confirm selection');
    		console.log(data);
    	})
    };

    studentManagementService.getRecruitmentStatus().then(function success(data) {
        $scope.recruitmentStatus = data.data;
        console.log('Recruitment status:');
        console.log($scope.recruitmentStatus);
    });
   
    
    
}

angular.module('appStudentManagement').$inject = ['$scope', '$filter'];

angular.module('appStudentManagement').directive("customSortStud", function () {
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
    };

 


});


angular.module('appStudentManagement')
    .controller('studentManagementController', ['$scope', '$filter','studentManagementService', studentManagementController]);


