/**
 * Created by dima on 30.04.16.
 */
function staffManagementController($scope, $filter, staffManagementService) {

    $scope.sort = {
        sortingOrder: 'id',
        reverse: false
    };
    $scope.gap = 5;

    $scope.filteredItems = [];
    $scope.groupedItems = [];
    $scope.itemsPerPage = 9;
    $scope.pagedItems = [];
    $scope.currentPage = 1;
    $scope.items = [];
    $scope.amount = 0;
    $scope.pageNums = 0;

    staffManagementService.showAllEmployees(1).success(function (data) {
        $scope.allEmployee = data;
    }, function error() {
        console.log("error");
    });

    //TODO id
    staffManagementService.getEmployeeRoles(74).success(function (data) {
        $scope.roles='';
        angular.forEach(data, function(value, key){
            $scope.roles+=value.roleName+" ";
        });
    }, function error() {
        console.log("error with getting Employee roles from service");
    });

    staffManagementService.getCountOfEmployee().success(function (data) {
        $scope.amount = Math.ceil(data / $scope.itemsPerPage);
        console.log($scope.pagedItems);
    });

    $scope.showAllEmployees = function showAllEmployees(pageNum) {
        staffManagementService.showAllEmployees(pageNum).success(function (data) {
            $scope.allEmployee = data;
        }, function error() {
            console.log("error");
        });
    }

    $scope.employees =
        [{roleName: 'ADMIN'},
            {roleName: 'SOFT'},
            {roleName: 'TECH'}];
    $scope.selection = [];

    $scope.toggleSelection = function toggleSelection(employeeName) {
        var idx = $scope.selection.indexOf(employeeName);

        if (idx > -1) {
            $scope.selection.splice(idx, 1);
        }
        else {
            $scope.selection.push({roleName: employeeName});
        }
        console.log($scope.selection);
    };

    $scope.addEmployee = function () {
        staffManagementService.addEmployee($scope.firstName, $scope.secondName,
            $scope.lastName, $scope.email, $scope.selection);
    };

    var searchMatch = function (haystack, needle) {
        if (!needle) {
            return true;
        }
        return haystack.toLowerCase().indexOf(needle.toLowerCase()) !== -1;
    };

    // init the filtered items
    $scope.search = function () {
        $scope.filteredItems = $filter('filter')($scope.items, function (item) {
            for (var attr in item) {
                if (searchMatch(item[attr], $scope.query))
                    return true;
            }
            return false;
        });
        // take care of the sorting order
        if ($scope.sort.sortingOrder !== '') {
            $scope.filteredItems = $filter('orderBy')($scope.filteredItems, $scope.sort.sortingOrder, $scope.sort.reverse);
        }
        $scope.currentPage = 1;
        // now group by pages
        //$scope.groupToPages();
    };

    $scope.range = function (size, start, end) {
        var ret = [];
        console.log(size, start, end);

        if (size < end) {
            end = size;
            start = size - $scope.gap;
        }
        for (var i = start; i < end; i++) {
            if (i >= 0)
                ret.push(i);
        }
        console.log(ret);
        return ret;
    };

    $scope.prevPage = function () {
        if ($scope.currentPage > 0) {
            $scope.currentPage--;
        }
    };

    $scope.nextPage = function () {
        if ($scope.currentPage < $scope.pagedItems.length - 1) {
            $scope.currentPage++;
        }
    };

    $scope.setPage = function () {
        $scope.currentPage = this.n+1;
        $scope.showAllEmployees($scope.currentPage);
        console.log($scope.currentPage);
    };

    // functions have been describe process the data for display
    $scope.search();

    var editRoles = [];

    $scope.showUserData = function (employee) {
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
        staffManagementService.showAssigned(employee.email);
    };
    var currentEmployee;
    $scope.getEmployee = function (employee) {
        currentEmployee = employee;
    };

    $scope.deleteEmployee = function () {
        console.log(currentEmployee)
        staffManagementService.deleteEmployee(currentEmployee.email);
    };

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
                    return ('icon-chevron-' + ((scope.sort.reverse) ? 'down' : 'up'));
                }
                else {
                    return 'icon-sort'
                }
            };
        }// end link
    }
});


angular.module('appStaffManagement')
    .controller('staffManagementController', ['$scope', '$filter', 'staffManagementService', staffManagementController]);