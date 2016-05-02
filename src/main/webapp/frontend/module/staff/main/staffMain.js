/**
 * Created by Vova on 02.05.2016.
 */

'use strict';

var app = angular.module('appStaffMain', ['appStaffMenu','appHeader','appFooter']);

app.directive('appStaffMenu',function () {
    return {
        templateUrl: 'module/staff/menu/staffMenu.html',
        controller: 'staffMenuController'
    };
});