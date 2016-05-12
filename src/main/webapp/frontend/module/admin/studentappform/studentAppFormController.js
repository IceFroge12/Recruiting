/**
 * Created by Admin on 12.05.2016.
 */
function adminStudentFormController($scope, appFormStudentService,$routeParams) {

    $scope.showAppForm = function showAppForm() {
        var id = $routeParams.id;
        appFormStudentService.getAppForm(id).success(function (data) {
            $scope.appForm = data;
            console.log(data);
        }, function error() {
            console.log("error");
        });
    };
    
}

angular.module('appFormStudents')
    .controller('adminStudentFormController', ['$scope','appFormStudentService', '$routeParams',adminStudentFormController]);