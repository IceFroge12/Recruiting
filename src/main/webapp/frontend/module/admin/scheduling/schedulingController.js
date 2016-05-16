/**
 * Created by dima on 30.04.16.
 */
function schedulingController($scope, schedulingService) {

    $scope.activeDate;
    $scope.selectedDates = [];
    $scope.timeResult = [];
    $scope.type = 'individual';

    $scope.removeFromSelected = function(dt) {
        $scope.selectedDates.splice(this.selectedDates.indexOf(dt), 1);
        angular.forEach($scope.timeResult, function (value, key) {
            if (value.data === dt.val){
                $scope.timeResult.splice(key, 1);
            }
        });
    };
    
    $scope.setTime = function (starTime, endTime, date) {
        var temp = {
            date : date,
            hourStart : starTime.getHours(),
            minutesStart : starTime.getMinutes(),
            hoursEnd : endTime.getHours(),
            minutesEnd : endTime.getMinutes()
        };
        if (!$scope.timeResult.length > 0){
            $scope.timeResult.push(temp);
        }
        angular.forEach($scope.timeResult, function (value, key) {
            if (value.data === date.val){
                $scope.timeResult[key] = temp;
            }else {
                $scope.timeResult.push(temp);
            }
        });
    };
    
    schedulingService.getCurrentRecruitmentCountStudents().then(function success(data) {
        $scope.countStudent = data.amountOfStudents;
        $scope.amountOfTech = data.amountOfTech;
        $scope.amountOfSoft = data.amountOfSoft;
    });
    
    

}

angular.module('appScheduling')
    .controller('schedulingController', ['$scope','schedulingService', schedulingController]);