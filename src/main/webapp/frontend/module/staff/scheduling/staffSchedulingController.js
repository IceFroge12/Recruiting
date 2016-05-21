/**
 * Created by Vova on 02.05.2016.
 */

function staffSchedulingController($scope,$sce, staffSchedulingService) {

    staffSchedulingService.getUserTimePriorities().then(function success(data) {

        $scope.timePoints = data;

        angular.forEach($scope.timePoints, function (item, i) {
            item.i = i;
        });


        vm.timespanClicked = function(date) {
            console.log("DAT"+date);
            angular.forEach($scope.timePoints, function (it, i) {
                if(date==it.timePoint){
                    console.log("lol");
                }
            });
            vm.lastDateClicked = date;
        };
        
        // // console.log($scope.timePoints);

        angular.forEach($scope.timePoints, function (item, i) {
            $scope.k = i;

            vm.events.push(
                {
                    type: 'info',
                    startsAt: new Date(parseInt(item.timePoint)),
                    draggable: true,
                    resizable: true
                });
            $scope.k++;
            $scope.currentDate = item.timePoint;
        });
        
    });


    var vm = this;

    vm.events = [];
    vm.calendarView = 'month';
    vm.viewDate = moment().startOf('month').toDate();
    vm.isCellOpen = true;

    $scope.sce = $sce;



    
    // $scope.save = function () {
    //     console.log("Save");
    //     staffSchedulingService.saveTimePriorities($scope.timePoints);
    // };
    


}

angular.module('appStaffScheduling')
    .controller('staffSchedulingController', ['$scope','$sce', 'staffSchedulingService', staffSchedulingController]);