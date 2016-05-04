/**
 * Created by Vova on 02.05.2016.
 */

function staffStudentManagementController($scope, $http,  staffStudentManagementService) {
	$http.get('../../staff/assigned').success(function(data) {
		$scope.assignedStudents = data;
		console.log($scope.assignedStudents);
	});
	
	
	$scope.searchApplicationForm = function(id) {
		$http.get('../../staff/getById/' + id).success(function(data) {
			$scope.foundStudent = data;
			console.log($scope.foundStudent);
		});
	}
	
	$scope.assignApplicationForm = function(id) {
		$http.get('../../staff/assign/' + id).success(function(data) {
			$scope.assigningResult = data;
			console.log($scope.assigningResult);
		});
	}
	
	$scope.deassignApplicationForm = function(id) {
		$http.get('../../staff/deassign/' + id).success(function(data) {
			$scope.deassigningResult = data;
			console.log($scope.deassigningResult);
		});
	}
}

angular.module('appStaffStudentManagement')
    .controller('staffStudentManagementController', ['$scope', '$http', 'staffStudentManagementService',staffStudentManagementController]);