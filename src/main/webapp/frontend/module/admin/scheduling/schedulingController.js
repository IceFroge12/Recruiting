/**
 * Created by dima on 30.04.16.
 */
function schedulingController($scope, schedulingService) {

    $scope.activeDate;
    $scope.selectedDates = [];
    $scope.timeResult = [];
    $scope.type = 'individual';

    $scope.softAmountSet;
    $scope.techAmountSet;

    $scope.map = {};


    $scope.removeFromSelected = function (dt) {
        $scope.selectedDates.splice(this.selectedDates.indexOf(dt), 1);
        if ($scope.map[dt].id != -1) {
            schedulingService.deleteSelectedDayService($scope.map[dt].id).then(function (response) {
                console.log(response);
            })
        }
    };
    
    $scope.edit = function (object, date) {
        var temp = {
            id : object.id,
            day: date,
            hourStart: object.startTime,
            hourEnd: object.endTime,
            changed: false
        };
        schedulingService.editSelectedDayService(temp).then(function (response) {
            console.log(response);
        })
    };

    $scope.setTime = function (object, date) {
        if (object.id === -1){
            var temp = {    
                day: date,
                hourStart: object.startTime,
                hourEnd: object.endTime,
                changed: false
            };
            schedulingService.saveSelectedDayService(temp).then(function (response) {
                $scope.map[date].id = response.data;
            });
        }
    };

    $scope.saveSelectedDay = function () {
        console.log($scope.timeResult);
        schedulingService.saveSelectedDayService($scope.timeResult).then(function (response) {

        })
    };

    $scope.init = function () {
        schedulingService.getSelectedDayService().then(function (response) {
            $scope.selectedDates.length = 0;
            $scope.timeResult.length = 0;
            angular.forEach(response.data, function (value, key) {
                var foo = new Date(value.startDate).setHours(0, 0, 0, 0);
                $scope.selectedDates.push(foo);
                $scope.map[foo] = {
                    id: value.id,
                    startTime: new Date(value.startDate).getHours(),
                    endTime: new Date(value.endDate).getHours()
                };
                console.log(response.data);
            });
            console.log($scope.map);
        });
    };

    $scope.setStartHours = function (date, startTime) {

    };
    
    $scope.createMap = function (date) {
        if ($scope.map[date] === undefined){
            $scope.map[date] = {
                id : -1,
                startTime : 0,
                endTime :0
            }
        }
    };


    schedulingService.getCurrentRecruitmentCountStudents().then(function success(data) {
        $scope.countStudent = data.amountOfStudents;
        $scope.amountOfTech = data.amountOfTech;
        $scope.amountOfSoft = data.amountOfSoft;
    });


    schedulingService.getCurrentSchedule().then(function success(data) {
        console.log(data);
        $scope.schedulePoints = data.data;
        $scope.collapsed = [];
        $scope.collapsed[1] = [];
        $scope.collapsed[2] = [];
        $scope.collapsed[3] = [];
        for(var i=0; i<$scope.schedulePoints.length; i++){
            $scope.collapsed[1][i] = false;
            $scope.collapsed[2][i] = false;
            $scope.collapsed[3][i] = false;
        }
    })
    
    

}

angular.module('appScheduling')
    .controller('schedulingController', ['$scope', 'schedulingService', schedulingController]);