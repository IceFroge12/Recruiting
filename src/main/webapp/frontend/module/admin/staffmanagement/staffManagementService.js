/**
 * Created by dima on 30.04.16.
 */
'use strict';

function staffManagementService(http) {

    var service = {};
    
    service.showAllEmployees = function () {
        return http.post('/admin/showAllEmployee').then(function (response) {
            return response.data;
        });
    };

    service.addEmployee = function (firstName, secondName, lastName, email, roles) {
        http({
            method : 'POST',
            url : '/admin/addEmployee',
            contentType: 'application/json',
            data : JSON.stringify({
                firstName: firstName,
                secondName: secondName,
                lastName: lastName,
                email: email,
                roleList: roles
            })
        })
    };


    service.editEmployee = function (firstName, secondName, lastName, email, roles) {
        http({
            method : 'POST',
            url : '/admin/editEmployee',
            contentType: 'application/json',
            data : {
                firstName: firstName,
                secondName: secondName,
                lastName: lastName,
                email: email,
                roleList: roles
            }
        });
    };

    service.changeEmployeeStatus = function (email) {
        console.log(email)
        http({
            method : 'GET',
            url : '/admin/changeEmployeeStatus',
            data : { email: email }
        });
    };


    return service;
}



angular.module('appStaffManagement')
    .service('staffManagementService', ['$http', staffManagementService]);