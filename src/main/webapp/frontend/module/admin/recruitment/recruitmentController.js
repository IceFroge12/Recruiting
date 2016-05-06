/**
 * Created by dima on 30.04.16.
 */
function recruitmentController($scope, recruitmentService) {

    $scope.save = function () {
        console.log($scope.date1);
        console.log($scope.date2);
        console.log($scope.general);
        console.log($scope.advanced);
        recruitmentService.addRecruitment($scope.recruitmentName ,$scope.date1, $scope.date2, $scope.general, $scope.advanced);
    };

}

angular.module('appRecruitment')
    .controller('recruitmentController', ['$scope', 'recruitmentService', recruitmentController]);