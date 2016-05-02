/**
 * Created by dima on 30.04.16.
 */

function menuController($scope) {
    console.log("menu");
}

angular.module('appMenu')
    .controller('menuController', ['$scope', menuController]);