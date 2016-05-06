/**
 * Created by IO on 06.05.2016.
 */

function recoverRequestPageController($scope, $http) {
    $scope.emailSend = false;

    $scope.sendEmail = function () {
        console.log($scope.email);
        $http({
            method: 'POST',
            url: '/recoverPassword',
            contentType: 'application/json',
            data: {email: $scope.email}
        }).success(function (data, status) {
            console.log(data);
            console.log(status);
            if(status != 409){
                $scope.emailSend = true;
                $scope.email = data.email;
            }
        }).error(function (data, status, headers) {
            console.log(data);
        });
    }
    
}

angular.module('appRecoverRequestPage')
    .controller('recoverRequestPageController', ['$scope', '$http', recoverRequestPageController]);