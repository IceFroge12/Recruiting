'use strict';
angular.module('appHome', ['appHeader','appFooter','appMenuMain',"appSecondHeader"]);

app.directive('appHeader', function () {
    return {
        templateUrl: 'module/header/header.html',
        controller: 'headerController'
    };
});

app.directive('appSecondHeader', function () {
    return {
        templateUrl: 'module/home/secondheader/secondheader.html',
        controller: 'secondHeaderController'
    };
});

app.directive('appMenuMain',function () {
    return {
        templateUrl: 'module/home/menu/mainMenu.html',
        controller: 'menuController'
    };
});

app.directive('appFooter',function () {
    return {
        templateUrl: 'module/footer/footer.html',
        controller: 'footerController'
    };
});


