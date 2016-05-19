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

    $scope.roleSoft = 5;
    $scope.roleTech = 2;
    $scope.roleStudent = 3;
    $scope.roleToShow = 2;
    $scope.timePointToChange;
    
    // $scope.softArray = {};
    // $scope.techArray = {};
    // $scope.studentArray = {};

    $scope.map = {};
    $scope.softMap = {};
    $scope.techMap = {};
    $scope.studentMap = {};

    $scope.currentStatus = '';


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
            id: object.id,
            day: date,
            hourStart: object.startTime,
            hourEnd: object.endTime,
            changed: false
        };
        schedulingService.editSelectedDayService(temp).then(function (response) {
            console.log(response);
        })
    };

    $scope.deleteSoftTimeFinal = function(soft, timePoint){
        schedulingService.deleteUserTimeFinal(soft.id, timePoint.id).then(function (response) {
        if(response.status===200){
            var index=$scope.softMap[timePoint.id].indexOf(soft);
            $scope.softMap[timePoint.id].slice(index,1);
        }
        });
        console.log("delete Soft TimeFinal with id = "+soft.id +" and timePoint id = "+timePoint.id);
    };

    $scope.deleteTechTimeFinal = function(tech, timePoint){
        schedulingService.deleteUserTimeFinal(tech.id, timePoint.id).then(function (response) {
            if(response.status===200){
                var index=$scope.techMap[timePoint.id].indexOf(tech);
                $scope.techMap[timePoint.id].slice(index,1);
            }
        });
        console.log("delete Tech TimeFinal with id = "+tech.id +" and timePoint id = "+timePoint.id);
    };
    
    $scope.deleteStudentTimeFinal = function(student, timePoint){
        schedulingService.deleteUserTimeFinal(student.id, timePoint.id).then(function (response) {
            if(response.status===200){
                var index=$scope.studentMap[timePoint.id].indexOf(student);
                $scope.studentMap[timePoint.id].slice(index,1);
            }
        });
        console.log("delete Student imeFinal with id = "+student.id +" and timePoint id = "+timePoint.id);
    };

    $scope.setTime = function (object, date) {
        if (object.id === -1) {
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
        if ($scope.map[date] === undefined) {
            $scope.map[date] = {
                id: -1,
                startTime: 0,
                endTime: 0
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
        for (var i = 0; i < $scope.schedulePoints.length; i++) {
            $scope.collapsed[1][i] = false;
            $scope.collapsed[2][i] = false;
            $scope.collapsed[3][i] = false;
        }
    });

    $scope.getSoft = function (idTimePoint) {
        schedulingService.getSoftService(idTimePoint).then(function (response) {
            $scope.softMap[idTimePoint] = response.data;
        })
    };

    $scope.getTech = function (idTimePoint) {
        schedulingService.getTechService(idTimePoint).then(function (response) {
            $scope.techMap[idTimePoint] = response.data;
        })
    };

    $scope.getStudent = function (idTimePoint) {
        schedulingService.getStudentService(idTimePoint).then(function (response) {
            $scope.studentMap[idTimePoint] = response.data;
        })
    };

    $scope.submitDaysSelection = function () {
        schedulingService.confirmDaysSelectionService().then(function (response) {
            if (response.status === 200) {
                $scope.currentStatus = schedulingService.getConfirmDaysSelectionStatus();
            }
        })
    };

    $scope.saveInterviewParameters = function (softDuration, techDuration) {
        schedulingService.saveInterviewParametersService(softDuration, techDuration).then(function (response) {

        })
    };

    $scope.getInterviewParameters = function () {
        schedulingService.getInterviewParametersService().then(function (response) {
            if (response.status === 200) {
                $scope.softDuration = response.data.softDuration;
                $scope.techDuration = response.data.techDuration;
            }
        })
    }

    $scope.submitInterviewParameters = function () {
        schedulingService.confirmInterviewParametersService().then(function (response) {
            if (response.status === 200){
                $scope.currentStatus = schedulingService.getConfirmInterviewParametersStatus();
            }
        })
    }

    $scope.possibleToAdd = [];

    schedulingService.getUsersWithoutInterview($scope.roleSoft).then(function success(data){
        $scope.possibleToAdd[$scope.roleSoft] = data;
        console.log($scope.possibleToAdd);
    });

    schedulingService.getUsersWithoutInterview($scope.roleTech).then(function success(data){
        $scope.possibleToAdd[$scope.roleTech] = data;
    });

    schedulingService.getUsersWithoutInterview($scope.roleStudent).then(function success(data){
        $scope.possibleToAdd[$scope.roleStudent] = data;
        console.log($scope.possibleToAdd);
    });

    $scope.addUserToTimepoint = function addUserToTimepoint(id,idTimePoint){
        schedulingService.addUserToTimepoint(id,idTimePoint).then(function(){
            var index = findInPossible(id, $scope.possibleToAdd[$scope.roleToShow].data);
            $scope.possibleToAdd[$scope.roleToShow].data.splice(index, 1);
            switch($scope.roleToShow){
                case $scope.roleSoft:
                    $scope.schedulePoints[findInPossible(idTimePoint, $scope.schedulePoints)].amountOfSoft++;
                    break;
                case $scope.roleTech:
                    $scope.schedulePoints[findInPossible(idTimePoint, $scope.schedulePoints)].amountOfTech++;
                    break;
                case $scope.roleStudent:
                    $scope.schedulePoints[findInPossible(idTimePoint, $scope.schedulePoints)].amountOfStudents++;
                    break;
            }
        });
    };

    var findInPossible = function findInPossible(id, list){
        for(var i=0; i< list.length; i++){
          if(list[i].id===id)
          return i;
      }
        return -1;
    };

    $scope.modalShown = false;
    $scope.toggleModal = function(idRole, timePointToChange) {
        $scope.timePointToChange = timePointToChange;
        $scope.roleToShow = idRole;
        $scope.modalShown = !$scope.modalShown;
    };

}

angular.module('appScheduling').directive('modalDialog', function() {
    return {
        restrict: 'E',
        scope: {
            show: '='
        },
        replace: true,
        transclude: true,
        link: function(scope, element, attrs) {
            scope.dialogStyle = {};

            if (attrs.width) {
                scope.dialogStyle.width = attrs.width;
            }

            if (attrs.height) {
                scope.dialogStyle.height = attrs.height;
            }

            scope.hideModal = function() {
                scope.show = false;
            };
        },
        template: "<div class='ng-modal' ng-show='show'>" +
        "<div class='ng-modal-dialog' ng-style='dialogStyle'>" +
        "<div class='ng-modal-close' ng-click='hideModal()'>X</div>" +
        "<div class='ng-modal-dialog-content' ng-transclude>" +
        "</div>" +
        "</div>" +
        "</div>"
    };
});

angular.module('appScheduling')
    .controller('schedulingController', ['$scope', 'schedulingService', schedulingController]);