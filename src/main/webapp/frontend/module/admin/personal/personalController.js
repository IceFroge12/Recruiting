/**
 * Created by dima on 30.04.16.
 */

function personalController($scope, personalService) {
    
    $scope.changePassword = function () {
        $scope.oldPassword;
        $scope.newPassword;
        $scope.confirmNewPassword;
        
        console.log($scope.oldPassword+  $scope.newPassword+$scope.confirmNewPassword);
        
        personalService.changePassword($scope.oldPassword, $scope.newPassword).then(function success(data) {
            console.log(data.data);
        });
    };
}

angular.module('appPersonal')
    .controller('personalController', ['$scope', 'personalService',personalController]);