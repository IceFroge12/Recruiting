/**
 * Created by dima on 30.04.16.
 */

function formSettingsController($scope, ngToast, $sce, formAppService) {

    $scope.getMandatory = function () {
        var role = "ROLE_ADMIN";
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

        $scope.editVariant = [];
        angular.forEach(question.variants, function (item, i) {
            if (i == 0) {
                $scope.editVariant += item.variant;
            } else {
                $scope.editVariant += " , " + item.variant;
            }
        });
    };

    $scope.roles = ["MANDATORY", "ROLE_TECH", "ROLE_SOFT"];
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
    var selectRoleValue;
    $scope.showSelectRoleValue = function (myRoleSelect) {
        console.log(myRoleSelect);
        selectRoleValue = myRoleSelect;
    };

    function splitString(stringToSplit, separator) {
        return stringToSplit.split(separator);
    };

    $scope.changeStatus = function (employee) {
        console.log(employee);
        formAppService.changeQuestionStatus(employee.id);
    };

    $scope.saveForm = function () {
        var comma = ',';
        if ($scope.addVariant != null) {
            var variantArray = splitString($scope.addVariant, comma);
        } else {
            variantArray = [];
        }
            
        var role;
        if (selectRoleValue == "MANDATORY") {
            role = "ROLE_ADMIN";
        } else {
            role = selectRoleValue;
        }
        formAppService.addQuestion($scope.addText, selectedValue, selectActiveValue, variantArray, role);
    };

    $scope.editEmployee = function () {
        
        $scope.question.title = $scope.text;
        
        $scope.question.type = $scope.type;
        
        var comma = ',';
        var variantArray = splitString($scope.editVariant, comma);
        var variants = [];
        angular.forEach(variantArray, function (item, i) {
            variants.push({variant: item});
        });
        $scope.question.variants = variants;
        formAppService.editQuestion($scope.question.id, $scope.question.title, $scope.question.type, $scope.question.enable, variantArray, "ROLE_ADMIN");
    }


};

angular.module('appAdminForm')
    .controller('formSettingsController', ['$scope', 'ngToast', '$sce', 'formSettingsService', formSettingsController]);