/**
 * Created by dima on 30.04.16.
 */

function staffPersonalController($scope,staffPersonalService) {

    $scope.changePassword = function () {
        $scope.oldPassword;
        $scope.newPassword;
        $scope.confirmNewPassword;

        console.log($scope.oldPassword+  $scope.newPassword+$scope.confirmNewPassword);

        staffPersonalService.changePassword($scope.oldPassword, $scope.newPassword).then(function success(data) {
            console.log(data.data);
        });
    };

}

angular.module('appStaffPersonal')
    .controller('staffPersonalController', ['$scope', 'staffPersonalService', staffPersonalController]);