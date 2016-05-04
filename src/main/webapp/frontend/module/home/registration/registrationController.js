/**
 * Created by dima on 30.04.16.
 */

function registrationController($scope, $http) {


    $scope.registerStudent = function () {
        
        var test = {firstName: $scope.firstName, secondName: $scope.secondName, lastName: $scope.lastName, email: $scope.email, password: $scope.password};
        console.log(test);
        $http({
            method: 'POST',
            url: '/registrationStudent',
            contentType: 'application/json',
            data: {firstName: $scope.firstName, secondName: $scope.secondName, lastName: $scope.lastName, email: $scope.email, password: $scope.password}
        }).success(function (data, status, headers) {
            if (status === 409){
               // TODO show message user already exist!!!
            }
        }).error(function (data, status, headers) {
            console.log(status);
        });
    }
}

angular.module('appRegistration')
    .controller('registrationController', ['$scope', '$http', registrationController]);