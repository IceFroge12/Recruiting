/**
 * Created by dima on 30.04.16.
 */

function formSettingsController($scope, ngToast, $sce, formAppService) {

    $scope.questionRole;

    $scope.getMandatory = function () {
        var role = "ROLE_STUDENT";
        $scope.questionRole = "Student question";
        $scope.questions = [];
        getAllQuestion(role);
    };

    $scope.getSoft = function () {
        var role = "ROLE_SOFT";
        $scope.questionRole = "Soft question";
        $scope.questions = [];
        getAllQuestion(role);
    };

    $scope.getTech = function () {
        var role = "ROLE_TECH";
        $scope.questionRole = "Tech question";
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
        if (selectedValue == "input" || selectedValue == "textarea") {
            $scope.canMoveForward = true;
            $scope.addVariant = null;
        } else {
            var myToastMsg = ngToast.info({
                content: 'You have to input variants separated by comma',
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

    function splitString(stringToSplit, separator) {
        return stringToSplit.split(separator);
    };

    $scope.changeStatus = function (question) {
        console.log(question);
        formAppService.changeQuestionStatus(question.id).success(function (data) {
            console.log(data);
            question.enable = data;
        })
    };

    $scope.changeMandatoryStatus = function (question) {
        console.log(question);
        formAppService.changeQuestionMandatoryStatus(question.id).success(function (data) {
            console.log(data);
            question.mandatory = data;
        })
    };


    $scope.changeOrder = function (addOrder) {

        var x = 0;
        var length = $scope.questions.length;
        while (x < length) {
            console.log($scope.questions[x].order);

            if (addOrder == $scope.questions[x].order) {
                console.log("error");
                var myToastMsg = ngToast.info({
                    content: 'Duplicate order number',
                    timeout: 5000,
                    horizontalPosition: 'center',
                    verticalPosition: 'bottom',
                    dismissOnClick: true,
                    combineDuplications: true,
                    maxNumber: 2
                });
                $scope.cantAddQuestion = true;
                break;
            } else {
                $scope.cantAddQuestion = false;
                x++;
            }
            console.log("break");
        }
    };

    $scope.changeEditOrder = function (questionOrder) {

        var x = 0;
        var length = $scope.questions.length;
        while (x < length) {
            console.log($scope.questions[x].order);

            if (questionOrder == $scope.questions[x].order) {
                console.log("error");
                var myToastMsg = ngToast.info({
                    content: 'Duplicate order number',
                    timeout: 5000,
                    horizontalPosition: 'center',
                    verticalPosition: 'bottom',
                    dismissOnClick: true,
                    combineDuplications: true,
                    maxNumber: 2
                });
                $scope.cantEditQuestion = true;
                break;
            } else {
                $scope.cantEditQuestion = false;
                x++;
            }
            console.log("break");
        }
    };

    $scope.changeEditType = function (type) {
        if (type == "input" || type == "textarea") {
            console.log("input Error");
            $scope.cantEditVariant = true;
            $scope.editVariant = null;
        } else {
            $scope.cantEditVariant = false;
        }

    };


    $scope.saveForm = function () {
        var comma = ',';
        if ($scope.addVariant != null) {
            var variantArray = splitString($scope.addVariant, comma);

        } else {
            variantArray = [];
        }
        
        var role;
        if ($scope.questionRole == "Student question") {
            role = "ROLE_STUDENT";
        }
        if ($scope.questionRole == "Tech question") {
            role = "ROLE_TECH";
        }
        if ($scope.questionRole == "Soft question") {
            role = "ROLE_SOFT";
        }
        
        formAppService.addQuestion($scope.addText, selectedValue, selectMandatoryValue, selectActiveValue, variantArray, role, $scope.addOrder);
    };


    $scope.saveEditQuestion = function () {

        $scope.question.title = $scope.text;

        $scope.question.type = $scope.type;

        var comma = ',';
        if ($scope.question.type == "input" || $scope.question.type == "textarea") {
            console.log("input Error");
        } else {
            $scope.cantEditVariant = false;
            var variantArray = splitString($scope.editVariant, comma);
        }
        console.log(variantArray);

        $scope.question.order = $scope.questionOrder;

        formAppService.editQuestion($scope.question.id, $scope.question.title, $scope.question.type, $scope.question.enable, variantArray, "ROLE_STUDENT", $scope.question.order);
    }

    $scope.finalMarks = [0, 1, 2, 3];

    formAppService.getDecisionMatrix().then(function success(data) {
        $scope.decisionMatrix = data.data;
        console.log($scope.decisionMatrix);
    });

    $scope.saveDecisionMatrix = function () {
        formAppService.saveDecisionMatrix($scope.decisionMatrix).then(function success(data) {
            console.log('Updating decision');
            console.log(data);
            $scope.resultMessage = data.data;
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