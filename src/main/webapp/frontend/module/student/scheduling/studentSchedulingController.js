/**
 * Created by IO on 02.05.2016.
 */


function studentSchedulingController($scope, $http) {
	$http.get('../../student/schedule').success(function(data) {
		$scope.schedule = data;
		console.log($scope.schedule);
		if ($scope.schedule.message != null) {
			$scope.resultMessage = data;
		}
		console.log($scope.schedule);
	});

	$scope.update = function() {
		var jsonData = angular.toJson($scope.schedule.timePoints);
		var req= $http.post('../../student/updateSchedule', $scope.schedule.timePoints);
		req.success(function(data) {
			$scope.resultMessage = data;
		});
	}
}

angular.module('studentScheduling')
    .controller('studentSchedulingController', ['$scope', '$http', 'studentSchedulingService', studentSchedulingController]);