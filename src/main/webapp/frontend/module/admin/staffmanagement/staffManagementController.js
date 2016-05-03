/**
 * Created by dima on 30.04.16.
 */
function staffManagementController($scope, staffManagementService) {

    staffManagementService.showAllEmployees().then(function success(data) {
        console.log(data);
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

    $scope.employees = [{roleName: 'ADMIN'},
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

    $scope.editEmployee = function (employee) {
        $scope.emailEdit = employee.email;
        $scope.firstNameEdit = employee.firstName;
        $scope.secondNameEdit = employee.secondName;
        $scope.lastNameEdit = employee.lastName;

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


        

        staffManagementService.editEmployee($scope.firstNameEdit, $scope.secondNameEdit,
            $scope.lastNameEdit, $scope.emailEdit, editRoles);

    }


    $scope.changeEmployeeStatus = function (employee) {
        console.log(employee.email)
        staffManagementService.changeEmployeeStatus(employee.email);
    }


}

angular.module('appStaffManagement')
    .controller('staffManagementController', ['$scope', 'staffManagementService', staffManagementController]);