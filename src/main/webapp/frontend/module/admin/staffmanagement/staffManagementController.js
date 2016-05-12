/**
 * Created by dima on 30.04.16.
 */
function staffManagementController($scope, $filter, $http, staffManagementService) {


    $scope.sort = {
        sortingOrder: 1,
        reverse: false
    };
    $scope.gap = 5;

    $scope.filteredItems = [];
    $scope.groupedItems = [];
    $scope.itemsPerPage = 9;
    $scope.currentPage = 1;
    $scope.items = [];
    $scope.amount = 0;
    $scope.sortingDir = 1;
    $scope.assignedStudents = [];

    // init the sorted items
    $scope.$watch("sort.reverse", function () {
        $scope.currentPage = 1;
        $scope.showAllEmployees($scope.currentPage);
        console.log($scope.currentPage);
        console.log($scope.sort.reverse);
        console.log($scope.sort.sortingOrder);
    });

    $scope.$watch("sort.sortingOrder", function () {
        $scope.currentPage = 1;
        $scope.showAllEmployees($scope.currentPage);
        console.log($scope.currentPage);
        console.log($scope.sort.reverse);
        console.log($scope.sort.sortingOrder);
    });

    staffManagementService.showAllEmployees(1, $scope.sort.sortingOrder, $scope.sort.reverse, true).success(function (data) { //TODO
        $scope.allEmployee = data;
    }, function error() {
        console.log("error");
    });

    staffManagementService.getCountOfEmployee().success(function (data) {
        $scope.amount = Math.ceil(data / $scope.itemsPerPage);
    });

    $scope.showAllEmployees = function showAllEmployees(pageNum) {
        var itemsByPage = 10;
        staffManagementService.showAllEmployees(pageNum, itemsByPage, $scope.sort.sortingOrder, true).success(function (data) { //TODO
            angular.forEach(data, function (value1, key1) {
                angular.forEach(value1.roles, function (value2, key2) {
                    value2.roleName = value2.roleName.slice(5);
                })
            });
            $scope.allEmployee = data;
            console.log(data);

        }, function error() {
            console.log("error");
        });
    };


    
    //NE TROGAT ROLI !!!!!!!!!!!!!
    $scope.employees =
        [{roleName: 'ROLE_ADMIN'},
            {roleName: 'ROLE_SOFT'},
            {roleName: 'ROLE_TECH'}];
    
    $scope.selection = [];

    
    $scope.toggleSelection = function toggleSelection(employeeName) {
        var idx = $scope.selection.indexOf(employeeName);
        
        if (idx > -1) {
            $scope.selection.splice(idx, 1);
        }
            
        else {
            $scope.selection.push(employeeName);
        }
        console.log($scope.selection);
    };

    $scope.addEmployee = function () {
        
        var roleArray = [];
        
        angular.forEach($scope.selection, function (item, i) {
            roleArray.push({roleName:item})
        });
        
        staffManagementService.addEmployee($scope.firstName, $scope.secondName,
            $scope.lastName, $scope.email, roleArray);
    };

    $scope.range = function (size, start, end) {
        var ret = [];
        console.log(size, start, end);


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
        if ($scope.currentPage < $scope.pagedItems.amount - 1) {
            $scope.currentPage++;
        }
    };

    $scope.setPage = function () {
        $scope.currentPage = this.n;
        $scope.showAllEmployees($scope.currentPage);
    };

    var editRoles = [];
  
    $scope.showUserData = function (employee) {
        $scope.adminEdit = false;
        $scope.softEdit = false;
        $scope.techEdit = false;
        $scope.id = employee.id;
        $scope.emailEdit = employee.email;
        $scope.firstNameEdit = employee.firstName;
        $scope.secondNameEdit = employee.secondName;
        $scope.lastNameEdit = employee.lastName;
        console.log(employee.roles);
        angular.forEach(employee.roles, function (item, i) {
            if (item.roleName == "ADMIN") {
                $scope.adminEdit = true;
                editRoles.push({roleName: item.roleName});
            }
            if (item.roleName == "SOFT") {
                $scope.softEdit = true;
                editRoles.push({roleName: item.roleName});
            }
            if (item.roleName == "TECH") {
                $scope.techEdit = true;
                editRoles.push({roleName: item.roleName});
            }
            //TODO change logic
        });
    
        editRoles = [];
        // editRoles.push({roleName: "ADMIN"});
    };

    $scope.editEmployee = function () {
        staffManagementService.editEmployee($scope.id, $scope.firstNameEdit, $scope.secondNameEdit,
            $scope.lastNameEdit, $scope.emailEdit, editRoles);
    };


    $scope.changeEmployeeStatus = function (employee) {
        staffManagementService.changeEmployeeStatus(employee.email).success(function (data) {
            console.log(data);
        });

        console.log($scope.dat);
        if ($scope.myClass === "btn-danger")
            $scope.myClass = "btn btn-info";
        else
            $scope.myClass = "btn-danger";
    };

    $scope.showAssigned = function (employee) {
        $http({
            method : 'POST',
            url : '/admin/showAssignedStudent',
            params : {email:employee.email}
        }).success(function (data, status, headers) {
            console.log(data);
            $scope.assignedStudents = data;
            return data;
        }).error(function (data, status, headers) {
            console.log(status);
        });
    };
    
    var currentEmployee;
    $scope.getEmployee = function (employee) {
        currentEmployee = employee;
    };

    $scope.deleteEmployee = function () {
        console.log(currentEmployee);
        staffManagementService.deleteEmployee(currentEmployee.email);
        var index = $scope.allEmployee.indexOf(currentEmployee);
        $scope.allEmployee.splice(index, 1);
    };
    
    $scope.deleteAssignedStudent = function () {
        console.log('deleteAssignedStudent');
        console.log(currentEmployee)
        staffManagementService.deleteAssignedStudent(currentEmployee);
        var index = $scope.assignedStudents.indexOf(currentEmployee);
        $scope.assignedStudents.splice(index, 1);
    };

    $scope.searchEmployee = function (employeeName) {
        console.log(employeeName);
        staffManagementService.searchEmployee(employeeName).success(function (data) {
            console.log(data);
            $scope.allEmployee = data;
        }, function error() {
            console.log("error");
        });
    }

}

angular.module('appStaffManagement').$inject = ['$scope', '$filter'];

angular.module('appStaffManagement').directive("customSort", function () {
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


angular.module('appStaffManagement')
    .controller('staffManagementController', ['$scope', '$filter', '$http', 'staffManagementService', staffManagementController]);