function feedbackController($scope, $rootScope, feedbackService) {

    $scope.init = function () {
        feedbackService.getFeedBack($rootScope.id).success(function (data) {
            $scope.feedBackText = data.feedBack;
        })
    };

    $scope.saveFeedBack = function (feedBack) {
        feedbackService.saveFeedBack(feedBack);
    };
}


angular.module('appFeedback').controller('feedbackController', ['$scope', '$rootScope', 'feedbackService', feedbackController]);
