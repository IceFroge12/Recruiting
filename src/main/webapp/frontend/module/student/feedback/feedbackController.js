function feedbackController($scope, feedback) {
    var self = this;
    self.appForm_id = null;
    self.feedback = null;

    self.setFeedback = function (id, feedback) {
        feedbackService.setFeedback(id, feedback).then(
            function (response) {
                //self.feedback=response.data;
            }, function (errResponse) {
                console.error('Error while seting feedback: controller.');
            })
    }
}


angular.module('appFeedback').controller('feedbackController', ['$scope', 'feedbackService', feedbackController]);
