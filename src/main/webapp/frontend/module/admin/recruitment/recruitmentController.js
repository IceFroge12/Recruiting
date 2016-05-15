/**
 * Created by dima on 30.04.16.
 */
function recruitmentController($scope, recruitmentService) {


    $("#datepicker , #datepicker1, #datepicker2").kendoDatePicker({
        format: "yyyy-mm-dd hh:mm:ss[.fffffffff]"
    });
    
    recruitmentService.getCurrentRecruitment().then(function success(data) {
        console.log(data.data);
        
        $scope.date1 = new Date(parseInt(data.registrationDeadline)).toString('yyyy-mm-dd hh:mm:ss[.fffffffff]');
        
        $scope.date2 = new Date(parseInt(data.scheduleChoicesDeadline)).toString('yyyy-mm-dd hh:mm:ss[.fffffffff]');
        
        $scope.date3 = new Date(parseInt(data.endDate)).toString('yyyy-mm-dd hh:mm:ss[.fffffffff]');
   
        $scope.general = data.maxGeneralGroup;
        $scope.advanced = data.maxAdvancedGroup;
        
        console.log($scope.date1);
        console.log($scope.date2);
        console.log($scope.date3);
        console.log($scope.general);
        console.log($scope.advanced);
    });
    

    $scope.save = function () {
        console.log($scope.date1);
        console.log($scope.date2);
        console.log($scope.date3);
        console.log($scope.general);
        console.log($scope.advanced);
        recruitmentService.addRecruitment($scope.recruitmentName, $scope.date1, $scope.date2, $scope.date3, $scope.general, $scope.advanced);
    };

    $scope.edit = function () {
        console.log($scope.date1);
        console.log($scope.date2);
        console.log($scope.date3);
        console.log($scope.general);
        console.log($scope.advanced);
        recruitmentService.editRecruitment($scope.recruitmentName, $scope.date1, $scope.date2, $scope.date3, $scope.general, $scope.advanced);
    };

    $scope.endRecruitment = function () {
        recruitmentService.endRecruitment();
    }

}

angular.module('appRecruitment')
    .controller('recruitmentController', ['$scope', 'recruitmentService', recruitmentController]);