/**
 * Created by dima on 30.04.16.
 */
'use strict';

function recruitmentService(http) {

    var service = {};

    service.addRecruitment  = function (name,registrationDeadline, scheduleChoicesDeadline, maxGeneralGroup, maxAdvancedGroup) {
        console.log(registrationDeadline+scheduleChoicesDeadline+maxAdvancedGroup);
        http({
            method : 'POST',
            url : '/admin/addRecruitment',
            contentType: 'application/json',
            data : JSON.stringify({
                name:name,
                registrationDeadline: registrationDeadline,
                scheduleChoicesDeadline: scheduleChoicesDeadline,
                maxGeneralGroup: maxGeneralGroup,
                maxAdvancedGroup: maxAdvancedGroup
            })
            // data: {data:'test'}
        });
    };
        
    return service;
}

angular.module('appRecruitment')
    .service('recruitmentService', ['$http', recruitmentService]);