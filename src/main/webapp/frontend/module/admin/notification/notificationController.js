/**
 * Created by dima on 30.04.16.
 */
function notificationController($scope, http, notificationService) {

    notificationService.getAllNotificationType().then(function success(data) {
        console.log(data);
        $scope.types = data;
    });


    $scope.showSelectValue = function (selectedType) {
        http({
            method: 'POST',
            url: '/admin/showTemplate',
            params: {title: selectedType}
        }).success(function (data) {
            console.log(data);
            $scope.topic = data.title;
            $scope.textNotification = data.text;
            $scope.emailTemplate = data;
            return data;
        });
    };

    $scope.save = function () {
        console.log($scope.emailTemplate.title);
        $scope.emailTemplate.title = $scope.topic;
        $scope.emailTemplate.text =  $scope.textNotification;
        console.log($scope.emailTemplate);
        notificationService.saveNotification($scope.emailTemplate);
    }

}

angular.module('appNotification')
    .controller('notificationController', ['$scope', '$http', 'notificationService', notificationController]);