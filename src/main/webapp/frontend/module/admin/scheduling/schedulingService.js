/**
 * Created by dima on 30.04.16.
 */
'use strict';

function schedulingService($http) {

    var service = {};
    service.getCurrentRecruitmentCountStudents = function () {
        return $http.get('/scheduling/getStudentCount').then(function (response) {
            console.log(response.data);
            return response.data;
        });
    };

    service.saveSelectedDayService = function (data) {
        return $http({
            method: 'POST',
            url: '/scheduling/saveSelectedDays',
            data: data
        })
    };

    service.getSelectedDayService = function () {
        return $http({
            method: 'POST',
            url: '/scheduling/getSelectedDays'
        })
    };

    service.deleteSelectedDayService = function (id) {
        return $http({
            method: 'GET',
            url: '/scheduling/deleteSelectedDay',
            params: {id: id}
        })
    };

    service.editSelectedDayService = function (data) {
        return $http({
            method: 'POST',
            url: '/scheduling/editSelectedDay',
            data: data
        })
    };


    service.getCurrentSchedule = function () {
        return $http({
            method: 'GET',
            url: '/scheduling/getCurrentSchedule'
        })
    };

    service.getUsersWithoutInterview = function (roleId) {
        return $http({
            method: 'GET',
            url: '/scheduling/getUsersWithoutInterview',
            params: {roleId: roleId}
        })
    };

    service.addUserToTomepoint = function (id,timePoint) {
        return $http({
            method: 'POST',
            url: '/scheduling/addUserToTomepoint',
            params: {userId:id, timePoint:timePoint}
        })
    };

    return service;
}

angular.module('appScheduling')
    .service('schedulingService', ['$http', schedulingService]);