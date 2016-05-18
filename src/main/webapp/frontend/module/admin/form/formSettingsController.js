/**
 * Created by dima on 30.04.16.
 */

function formSettingsController($scope, ngToast, $sce, formAppService) {

    $scope.getMandatory = function () {
        var role = "ROLE_STUDENT";
        $scope.questions = [];
        getAllQuestion(role);
    };

    $scope.getSoft = function () {
        var role = "ROLE_SOFT";
        $scope.questions = [];
        getAllQuestion(role);
    };

    $scope.getTech = function () {
        var role = "ROLE_TECH";
        getAllQuestion(role);
    };

    function getAllQuestion(role) {
        formAppService.getAllQuestion(role).then(function success(data) {
            for (var i = 0, len = data.length; i < len; i++) {
                if (typeof data[i].variants !== "undefined"
                    && data[i].variants !== "undefined") {
                    data[i].variants = JSON.parse(data[i].variants);
                    $scope.questions = data;
                    console.log($scope.questions);
                }
            }
            console.log($scope.questions);
        });
    }

    $scope.types = [];
    formAppService.getAllQuestionType().then(function success(data) {
        angular.forEach(data.data, function (item, i) {
            $scope.types.push(item.typeTitle);
        });
    });


    $scope.showQuestion = function (question) {

        $scope.question = question;

        $scope.text = question.title;

        $scope.type = question.type;

        $scope.questionOrder = question.order;

        $scope.editVariant = [];
        angular.forEach(question.variants, function (item, i) {
            if (i == 0) {
                $scope.editVariant += item.variant;
            } else {
                $scope.editVariant += " , " + item.variant;
            }
        });
    };

    $scope.roles = ["ROLE_STUDENT", "ROLE_TECH", "ROLE_SOFT"];
    $scope.sce = $sce;

    var selectedValue;
    $scope.showSelectValue = function (mySelect) {
        console.log(mySelect);
        selectedValue = mySelect;
        if (selectedValue == "input" || selectActiveValue == "textarea") {
            $scope.canMoveForward = true;
        } else {
            var myToastMsg = ngToast.info({
                content: 'a bla bla bla',//TODO
                timeout: 5000,
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
                dismissOnClick: true,
                combineDuplications: true,
                maxNumber: 2
            });
            $scope.canMoveForward = false;
        }
    };

    var selectActiveValue;
    $scope.showSelectActiveValue = function (myActiveSelect) {
        console.log(myActiveSelect);
        selectActiveValue = myActiveSelect;
    };

    var selectMandatoryValue;
    $scope.showSelectActiveValue = function (myMandatorySelect) {
        console.log(myMandatorySelect);
        selectMandatoryValue = myMandatorySelect;
    };

    var selectRoleValue;
    $scope.showSelectRoleValue = function (myRoleSelect) {
        console.log(myRoleSelect);
        selectRoleValue = myRoleSelect;
    };

    function splitString(stringToSplit, separator) {
        return stringToSplit.split(separator);
    };

    $scope.changeStatus = function (question) {
        console.log(question);
        formAppService.changeQuestionStatus(question.id).success(function(data){
            console.log(data);
            question.enable = data;
        })
    };

    $scope.changeMandatoryStatus = function (question) {
        console.log(question);
        formAppService.changeQuestionMandatoryStatus(question.id).success(function(data){
            console.log(data);
            question.mandatory=data;
        })
    };

    $scope.saveForm = function () {
        var comma = ',';
        if ($scope.addVariant != null) {
            var variantArray = splitString($scope.addVariant, comma);
       
        } else {
            variantArray = [];
        }

        var role = selectRoleValue;

        formAppService.addQuestion($scope.addText, selectedValue, selectMandatoryValue, selectActiveValue, variantArray, role, $scope.addOrder);
    };

    $scope.editEmployee = function () {

        $scope.question.title = $scope.text;

        $scope.question.type = $scope.type;

        var comma = ',';
        if ($scope.question.type == "input" || $scope.question.type == "textarea") {
            console.log("input Error")
        } else {
            var variantArray = splitString($scope.editVariant, comma);
        }

        var variants = [];
        angular.forEach(variantArray, function (item, i) {
            variants.push(item);
        });

        $scope.question.order = $scope.questionOrder;
        console.log("ORDER" + $scope.question.order);

        $scope.question.variants = variants;
        console.log($scope.question.variants);
        console.log($scope.question.type);
        formAppService.editQuestion($scope.question.id, $scope.question.title, $scope.question.type, $scope.question.enable, variants, "ROLE_STUDENT", $scope.question.order);
    }

    $scope.finalMarks = [1, 2, 3];
    
    formAppService.getDecisionMatrix().then(function success(data) {
        $scope.decisionMatrix = data.data;
        console.log($scope.decisionMatrix);
    });
    
    $scope.saveDecisionMatrix = function() {
    	formAppService.saveDecisionMatrix($scope.decisionMatrix).then(function success(data) {
    		console.log('Updating decision');
            console.log(data);
            $scope.resultMessage =  data.data;
			var toastMessage = {
	                content: $scope.resultMessage.message,
	                timeout: 5000,  
	                horizontalPosition: 'center',
	                verticalPosition: 'bottom',
	                dismissOnClick: true,
	                combineDuplications: true,
	                maxNumber: 2
	            };
			if ($scope.resultMessage.type == 'ERROR') {
				var myToastMsg = ngToast.warning(toastMessage);
			}
			else if ($scope.resultMessage.type == 'SUCCESS') {
				var myToastMsg = ngToast.success(toastMessage);
			}
        });
    }

};

angular.module('appAdminForm')
    .controller('formSettingsController', ['$scope', 'ngToast', '$sce', 'formSettingsService', formSettingsController]);