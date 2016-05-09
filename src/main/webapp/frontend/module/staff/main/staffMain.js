/**
 * Created by Vova on 02.05.2016.
 */

'use strict';

angular.module('appStaffMain', [])
    .directive('appStaffMenu', function () {
        return {
            templateUrl: 'module/staff/view/staffMenu.html',
            controller: 'staffMenuController'
        }
    });
