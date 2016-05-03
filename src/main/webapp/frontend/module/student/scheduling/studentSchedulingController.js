/**
 * Created by IO on 02.05.2016.
 */


function studentSchedulingController($scope, $http) {
	$http.get('../../student/schedule').success(function(data) {
		$scope.schedule = data;
		console.log($scope.schedule);
	});

	$scope.update = function() {
		var jsonData = angular.toJson($scope.schedule.timePoints);
		var req = $http.post('../../student/updateSchedule',
				$scope.schedule.timePoints);
		req.success(function() {
			$scope.infoMessage = 'Your priorities were updated.';
		});
	}
}

angular.module('studentScheduling')
    .controller('studentSchedulingController', ['$scope', '$http', 'studentSchedulingService', studentSchedulingController]);