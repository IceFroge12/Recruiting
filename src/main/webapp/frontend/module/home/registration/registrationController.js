/**
 * Created by dima on 30.04.16.
 */

function registrationController($scope, $http) {

    $scope.registerStudent = function () {
        $scope.emailSend = false;
        if ($scope.email === undefined || $scope.password === undefined ||
        $scope.lastName === undefined || $scope.secondName === undefined || $scope.firstName === undefined) {
            console.log("Error");
        } else {
            $http({
                method: 'POST',
                url: '/registrationStudent',
                contentType: 'application/json',
                data: {
                    firstName: $scope.firstName,
                    secondName: $scope.secondName,
                    lastName: $scope.lastName,
                    email: $scope.email,
                    password: $scope.password
                }
            }).success(function (data, status) {
                if (status === 409) {
                    // TODO show message user already exist!!!
                }
                if (status === 200) {
                    $scope.emailSend = true;
                    $scope.username = data.firstName;
                    $scope.email = data.email;
                }
            }).error(function (data, status) {
                console.log(status);
            });
        }

    }


}

angular.module('appRegistration')
    .controller('registrationController', ['$scope', '$http', registrationController]);