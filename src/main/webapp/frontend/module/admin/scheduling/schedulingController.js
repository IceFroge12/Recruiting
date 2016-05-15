/**
 * Created by dima on 30.04.16.
 */
function schedulingController($scope, schedulingService) {

    $scope.activeDate;
    $scope.selectedDates = [new Date().setHours(0, 0, 0, 0)];
    $scope.type = 'individual';

    $scope.removeFromSelected = function(dt) {
        $scope.selectedDates.splice(this.selectedDates.indexOf(dt), 1);
    };

    $scope.activeDate1;
    $scope.selectedDates1 = [new Date().setHours(24, 0, 0, 0)];
    $scope.type1 = 'individual';

    $scope.removeFromSelected1 = function(dt) {
        $scope.selectedDates1.splice(this.selectedDates1.indexOf(dt), 1);
    };

    
    schedulingService.getCurrentRecruitmentCountStudents().then(function success(data) {
        $scope.countStudent = data;
    });
    
}

angular.module('appScheduling')
    .controller('schedulingController', ['$scope','schedulingService', schedulingController]);