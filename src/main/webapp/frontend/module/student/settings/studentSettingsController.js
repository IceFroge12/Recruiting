/**
 * Created by Alona on 06.05.2016.
 */
function studentSettingsController($scope,studentSettingsService) {
    
    $scope.changePassword = function () {

        $scope.oldPassword;
        $scope.newPassword;
        $scope.confirmNewPassword;

        console.log($scope.oldPassword+  $scope.newPassword+$scope.confirmNewPassword);

        studentSettingsService.changePassword($scope.oldPassword, $scope.newPassword).then(function success(data) {
            console.log(data.data);
        });
    };
}

angular.module('appStudentSettings')
    .controller('studentSettingsController', ['$scope', 'studentSettingsService', studentSettingsController]);
