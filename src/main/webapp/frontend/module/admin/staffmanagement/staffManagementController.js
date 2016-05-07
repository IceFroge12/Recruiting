/**
 * Created by dima on 30.04.16.
 */
function staffManagementController($scope, $filter, staffManagementService) {

    staffManagementService.showAllEmployees(1).success(function (data) {
        $scope.allEmployee = data;
    }, function error() {
        console.log("error");
    });

    staffManagementService.getCountOfEmployee().success(function (data) {
        var itemsByPage = 9;
        var pages = Math.ceil(data / itemsByPage);

        $scope.range = [];
        for (var i = 1; i <= pages; i++) {
            $scope.range.push({index: i});
        }
        ;
        console.log($scope.range);
    })

    $scope.showAllEmployees = function showAllEmployees(pageNum) {
        staffManagementService.showAllEmployees(pageNum).success(function (data) {
            $scope.allEmployee = data;
        }, function error() {
            console.log("error");
        });
    };

//TODO id
    staffManagementService.getEmployeeRoles(74).success(function (data) {
        $scope.roles='';
        $scope.employeeRols = data;
        angular.forEach($scope.employeeRols, function(value, key){
            $scope.roles+=value.roleName+" ";
        });
    }, function error() {
        console.log("error with getting Employee roles from service");
    });



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
            $scope.selection.push({roleName: employeeName});
        }

        console.log($scope.selection);
    };


    // $scope.toggleSelection = function toggleSelection(employeeName) {

    // console.log(employeeName);
    // if(employeeName=="ROLE_ADMIN"){
    //     $scope.selection.push({roleName: employeeName});
    // }else{
    //     $scope.selection.pop(employeeName);
    // }
    //
    // console.log($scope.selection);


    // else {
    //     $scope.selection.push({roleName: employeeName});
    // }

    // };

    $scope.addEmployee = function () {
        staffManagementService.addEmployee($scope.firstName, $scope.secondName,
            $scope.lastName, $scope.email, $scope.selection);
    };


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

angular.module('appStaffManagement')
    .controller('staffManagementController', ['$scope', '$filter', 'staffManagementService', staffManagementController]);