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

    // service.getEmployeeRoles = function(employee){
    //     http.get('admin/getroles').success(function(data) {
    //         return data;
    //     });
    // };
    
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


    service.editEmployee = function (id,firstName, secondName, lastName, email, roles) {
        http({
            method : 'POST',
            url : '/admin/editEmployee',
            contentType: 'application/json',
            data : {
                id:id,
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
            params : {email:email}
        }).success(function (data, status, headers) {
            console.log(data);
                return data;
        });
    };


    service.showAssigned = function (email) {
        http({
            method : 'POST',
            url : '/admin/showAssignedStudent',
            params : {email:email}
        }).success(function (data, status, headers) {
            return data;
        }).error(function (data, status, headers) {
            console.log(status);
        });
    };


    service.deleteEmployee = function (email) {
        console.log(email);
        http({
            method : 'GET',
            url:'/admin/deleteEmployee',
            params:{email:email}
        }).error(function (data, status, headers) {
            console.log(status);
        });
    };

    return service;
}



angular.module('appStaffManagement')
    .service('staffManagementService', ['$http', staffManagementService]);