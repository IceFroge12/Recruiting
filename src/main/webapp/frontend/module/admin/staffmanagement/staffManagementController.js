/**
 * Created by dima on 30.04.16.
 */
function staffManagementController($scope, staffManagementService) {

    staffManagementService.showAllEmployees().then(function success(data) {
        var roleName = new String();
        $scope.allEmployee = data;
        console.log(data);
        angular.forEach(data, function (value, key) {
            angular.forEach(value.roles, function (item, i) {
                roleName += " " + item.roleName + " ";
                $scope.roleName = roleName;
            });
            roleName = new String();
        });
    }, function error() {
        console.log("error");
    });

   // staffManagementService.getEmployeeRoles(employee).then(function success(data){
   //     $scope.roles=data;
   // })



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


    var editRoles = [];

    $scope.showUserData = function (employee) {
        $scope.id = employee.id;
        $scope.emailEdit = employee.email;
        $scope.firstNameEdit = employee.firstName;
        $scope.secondNameEdit = employee.secondName;
        $scope.lastNameEdit = employee.lastName;
        console.log(employee.roles);
        angular.forEach(employee.roles, function (item, i) {
            if(item.roleName=="ADMIN"){
                $scope.adminEdit = true;
                editRoles.push({roleName: item.roleName});
            }
            if(item.roleName=="SOFT"){
                $scope.softEdit = true
                editRoles.push({roleName: item.roleName});
            }
            if(item.roleName=="TECH"){
                $scope.techEdit = true
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
        .controller('staffManagementController', ['$scope', 'staffManagementService', staffManagementController]);