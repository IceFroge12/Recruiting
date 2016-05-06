'use strict';

// function feedbackService(http) {
//     var service = {};
//
//     return service;
// }
//
// angular.module('appFeedback')
//     .service('feedbackService', ['$http', feedbackService]);

angular.module('appFeedback').factory('feedbackService',['$http', '$q', function($http, $q){
    return {
        setFeedback :function(id, feedback){
            return('/student/feedback/'+id, feedback).then(function(response){
                   // return response.data;
                },
                function(errResponse){
                    console.error('Error while set Feedback : Service');
                    return $q.reject(errResponse);
                });
        }

    };

}]);