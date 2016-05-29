/**
 * Created by Vova on 02.05.2016.
 */

function staffSchedulingController($scope,ngToast, staffSchedulingService) {


    var getSuccessToast = function (message) {
        var myToastMsg = ngToast.success({
            content: message,
            timeout: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
            dismissOnClick: true,
            combineDuplications: true,
            maxNumber: 2
        });
    };

    var getWarningToast = function (message) {
        var myToastMsg = ngToast.warning({
            content: message,
            timeout: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
            dismissOnClick: true,
            combineDuplications: true,
            maxNumber: 2
        });
    };


    staffSchedulingService.getUserFinalTimePoints().then(function success(data) {
        $scope.finalTimePoints = data;
        console.log($scope.finalTimePoints);
    });

    staffSchedulingService.getUserTimePriorities().then(function success(data) {
        
        $scope.timePoints = data;
        
        $scope.show = true;
        if($scope.timePoints == ""){
            $scope.show = false;
        }
        
        angular.forEach($scope.timePoints, function (item, i) {
            item.i = i;
        });
        console.log($scope.timePoints);
    });
    
    
  
    $scope.save = function () {
        console.log("Save");
        staffSchedulingService.saveTimePriorities($scope.timePoints).then(function (response) {
                console.log(response);
            getSuccessToast("Time priorities saved")
            }).catch(function (response) {
                getWarningToast('Can not saved time priorities');
            });
    }


}

angular.module('appStaffScheduling')
    .controller('staffSchedulingController', ['$scope', 'ngToast', 'staffSchedulingService', staffSchedulingController]);