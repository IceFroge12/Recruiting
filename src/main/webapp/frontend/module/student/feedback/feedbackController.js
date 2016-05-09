
function feedbackController($scope, feedbackService, feedback) {

    feedbackService.saveFeedback(feedback);

}


angular.module('appFeedback').controller('feedbackController', ['$scope', 'feedbackService', feedbackController]);
