/**
 * Created by dima on 30.04.16.
 */

'use strict';

var app = angular.module('appMain', ['appMenu','appHeader','appFooter']);
app.directive('appMenuAdmin', function () {
    return {
        templateUrl: 'module/admin/menu/menu.html',
        controller: 'menuController'
    };
});
