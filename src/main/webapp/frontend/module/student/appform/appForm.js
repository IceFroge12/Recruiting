/**
 * Created by dima on 02.05.16.
 */

'use strict';

angular.module('appStudentForm',[]);

app.directive('appMenuStudent', function () {
    return {
        templateUrl: 'module/student/menu/studentMenu.html',
        controller: 'studentMenuController'
    };
});