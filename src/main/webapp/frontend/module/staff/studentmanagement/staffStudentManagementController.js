/**
 * Created by Vova on 02.05.2016.
 */

function staffStudentManagementController($scope, $http, staffStudentManagementService) {
    var loadForms = function () {
        $http.get('../../staff/assigned').success(function (data) {
            $scope.assignedStudents = data;
            console.log($scope.assignedStudents);
        });
    };
    loadForms();
    $scope.search = {};
    $scope.searchApplicationForm = function (id) {
        $http.get('../../staff/getById/' + id).success(function (data) {
            console.log($scope.foundStudent);
            $scope.search.resultMessage = data;
            if ($scope.search.resultMessage.type != 'WARNING') {
                $scope.search.resultMessage.message == null;
                $scope.foundStudent = data;
            }
        });
    };

    $scope.assignApplicationForm = function (student) {
        $scope.assigning.id = student.id;
        console.log($scope.assigning);
        var req = $http.post('../../staff/assign/', $scope.assigning);
        req.success(function (data) {
            $scope.resultMessage = data;
            if ($scope.resultMessage.type == 'SUCCESS') {
                loadForms();
                $scope.newFormId = null;
                $scope.foundStudent = null;
                $('#assignNew').modal('hide');
            }
        });
    };


    $scope.deassignApplicationForm = function (id) {
        $http.get('../../staff/deassign/' + id).success(function (data) {
            $scope.resultMessage = data;
            if ($scope.resultMessage.type == 'SUCCESS') {
                loadForms();
                return;
                var index = $scope.assignedStudents.indexOf(student);
                $scope.assignedStudents.splice(index, 1);

            }
            console.log($scope.resultMessage);
        });
    };
    $scope.assigning = {};
    $scope.assigning.roles = [];
    $scope.chooseRoleForAssign = function (role) {
        var idx = $scope.assigning.roles.indexOf(role);
        if (idx > -1) {
            $scope.selection.splice(idx, 1);
        }
        else {
            $scope.assigning.roles.push(role);
        }
    };

    $scope.hasStudentMark = function(student) {
    	for(var i = 0; i < student.interviews.length; i++) {
    		if(!student.interviews[i].hasMark) {
    			return false;
    		}
    	}
    	return true;
    }
    
    $scope.roleNames = ['', 'Tech', '', '', 'Soft'];
}

angular.module('appStaffStudentManagement')
    .controller('staffStudentManagementController', ['$scope', '$http', 'staffStudentManagementService', staffStudentManagementController]);