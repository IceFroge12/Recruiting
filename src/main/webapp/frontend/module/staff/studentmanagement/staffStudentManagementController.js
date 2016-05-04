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
	
	$scope.assignApplicationForm = function(student) {
		$http.get('../../staff/assign/' + student.id).success(function(data) {
			$scope.resultMessage = data;
			if($scope.resultMessage.type == 'SUCCESS') {
				$scope.assignedStudents.push(student);
				$scope.newFormId = null;
				$scope.foundStudent = null;
				$('#assignNew').modal('hide');
			}
			console.log($scope.resultMessage);
		});
	}
	

		$scope.deassignApplicationForm = function(student) {
		$http.get('../../staff/deassign/' + student.id).success(function(data) {
			$scope.resultMessage = data;
			if($scope.resultMessage.type == 'SUCCESS') {
				var index = $scope.assignedStudents.indexOf(student);
				$scope.assignedStudents.splice(index, 1);
			} 
			console.log($scope.resultMessage);
		});
	}
}

angular.module('appStaffStudentManagement')
    .controller('staffStudentManagementController', ['$scope', '$http', 'staffStudentManagementService',staffStudentManagementController]);