/**
 * Created by Alona on 06.05.2016.
 */
function appFormStudentController($scope, appFormService) {
    appFormService.loadAppFormData().then(function success(data) {
        $scope.id =data.id;
        $scope.questions = data.questions;
        $scope.user = data.user;
        $scope.status = data.status;
        $scope.data = data;

    }, function error() {
        console.log("error");
    });

    $scope.changeUserName = function () {
        console.log("MDDDDDDDD");
        appFormService.changeUserName($scope.data);
    };

    $scope.toggle = function (item, list){
        var idx=-1;
        for(var i=0; i<list.length; i++){
            if(list[i].answer==item)
                idx=i;
        }
        if (idx >-1){
            list.splice(idx,1);
        }
        else {
            list.push({answer: item});
        }
        console.log(list);
    };

    $scope.exists = function (item, list){
        for(var i=0; i<list.length; i++){
            if(list[i].answer==item)
                return true;
        }
        return false;
    };


    var _URL = window.URL || window.webkitURL;

    $("#file").change(function() {

        var image, file;
        console.log(this.files[0]);

        if ((file = this.files[0])) {

            file.type.match("/.png/");
            image = new Image();

            image.onload = function() {

                if(this.width !=300 && this.height !=400){
                    alert("Wrong size");

                }
                else {
                    image.src = _URL.createObjectURL(file);
                }
            }}
        //image.src = _URL.createObjectURL(file);
    });

}



angular.module('appStaffAppForm')
    .controller('appFormStudentController', ['$scope','appFormStudentService', appFormStudentController]);
