/**
 * Created by dima on 30.04.16.
 */
function staffManagementController($scope,staffManagementService) {

    staffManagementService.showAllEmployees().then(function success(data) {
        console.log(data);
    }, function error() {
        console.log("error");
    });
}

angular.module('appStaffManagement')
    .controller('staffManagementController', ['$scope','staffManagementService' ,staffManagementController]);