/**
 * Created by Vova on 02.05.2016.
 */

function staffSchedulingController($scope, staffSchedulingService) {

    staffSchedulingService.getUserTimePriorities().then(function success(data) {

        $scope.timePoints = data;

        angular.forEach($scope.timePoints, function (item, i) {
            item.i = i;
        });
        console.log($scope.timePoints);
    });

    $scope.save = function () {
        console.log("Save");
        staffSchedulingService.saveTimePriorities($scope.timePoints);
    }


}

angular.module('appStaffScheduling')
    .controller('staffSchedulingController', ['$scope', 'staffSchedulingService', staffSchedulingController]);