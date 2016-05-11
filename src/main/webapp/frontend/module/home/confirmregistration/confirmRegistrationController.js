/**
 * Created by BOSS on 11.05.2016.
 */
function confirmRegistrationController($scope, $routeParams, confirmRegistrationService) {

    console.log($routeParams);
    confirmRegistrationService.confirmRegistration($routeParams.token).success(function (data) {
        console.log(data);
        $scope.result = data;
    });

}

angular.module('appConfirmRegistration')
    .controller('confirmRegistrationController', ['$scope', '$routeParams', 'confirmRegistrationService', confirmRegistrationController]);