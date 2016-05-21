/**
 * Created by dima on 30.04.16.
 */
function recruitmentController($scope, recruitmentService) {

    
    recruitmentService.getCurrentRecruitment().then(function success(data) {
        $scope.recId = data.id;
        console.log(data.data);

        var dateFormar = 'yyyy-MM-dd hh:mm';

        $scope.date1 = new Date(parseInt(data.registrationDeadline)).toString(dateFormar);
        
        $scope.date2 = new Date(parseInt(data.scheduleChoicesDeadline)).toString(dateFormar);
        
        $scope.date3 = new Date(parseInt(data.endDate)).toString(dateFormar);
   
        $scope.general = data.maxGeneralGroup;
        $scope.advanced = data.maxAdvancedGroup;
        $scope.recruitmentName= data.name;

        console.log($scope.date1);
        console.log($scope.date2);
        console.log($scope.date3);
        console.log($scope.general);
        console.log($scope.advanced);
    });

    var zeroSeconds = ':00';
    $scope.edit = function () {

        console.log($scope.date1);
        console.log($scope.date2);
        console.log($scope.date3);
        console.log($scope.general);
        console.log($scope.advanced);
        recruitmentService.editRecruitment($scope.recId, $scope.recruitmentName, $scope.date1 + zeroSeconds, $scope.date2 + zeroSeconds,
            $scope.date3+ zeroSeconds, $scope.general, $scope.advanced);
    };
    $scope.save = function () {
        console.log($scope.date1);
        console.log($scope.date2);
        console.log($scope.date3);
        console.log($scope.general);
        console.log($scope.advanced);
        recruitmentService.addRecruitment($scope.recruitmentName, $scope.date1 + zeroSeconds, $scope.date2+ zeroSeconds, $scope.date3+ zeroSeconds, $scope.general, $scope.advanced);
    };

    $scope.endRecruitment = function () {
        recruitmentService.endRecruitment();
    }

}

angular.module('appRecruitment')
    .controller('recruitmentController', ['$scope', 'recruitmentService', recruitmentController]);