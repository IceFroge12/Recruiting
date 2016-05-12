/**
 * Created by dima on 30.04.16.
 */
function schedulingController($scope, schedulingService) {
    
    schedulingService.getCurrentRecruitmentCountStudents().then(function success(data) {
        $scope.countStudent = data;
    });
    
}

angular.module('appScheduling')
    .controller('schedulingController', ['$scope','schedulingService', schedulingController]);