/**
 * Created by dima on 02.05.16.
 */

function appFormController($scope, appFormService, Upload) {

    appFormService.loadAppFormData().then(function success(data) {
        // $scope.id =data.id;
        $scope.questions = data.questions;
        $scope.user = data.user;
        $scope.status = data.status;
        $scope.data = data;

    }, function error() {
        console.log("error");
    });

    $scope.changeUserName = function (picFile) {
        console.log("MDDDDDDDD");
        //appFormService.changeUserName($scope.data);
        uploadPic(picFile);
    };  

    $scope.toggle = function (item, list) {
        var idx = -1;
        for (var i = 0; i < list.length; i++) {
            if (list[i].answer == item)
                idx = i;
        }
        if (idx > -1) {
            list.splice(idx, 1);
        }
        else {
            list.push({answer: item});
        }
        console.log(list);
    };

    $scope.exists = function (item, list) {
        for (var i = 0; i < list.length; i++) {
            if (list[i].answer == item)
                return true;
        }
        return false;
    };

    function uploadPic(file) {
        file.upload = Upload.upload({
            url: '/student/uploadPhoto',
            fields: {'username': 'test'}, // additional data to send
            file: file
        }).progress(function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
        }).success(function (data, status, headers, config) {
            console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
        });
    }

    // var _URL = window.URL || window.webkitURL;

    // $("#file").change(function() {
    //
    //     var image, file;
    //     console.log(this.files[0]);
    //
    //     if ((file = this.files[0])) {
    //
    //         file.type.match("/.png/");
    //         image = new Image();
    //
    //         image.onload = function() {
    //
    //            if(this.width !=300 && this.height !=400){
    //             alert("Wrong size");
    //
    //            }
    //             else {
    //                image.src = _URL.createObjectURL(file);
    //            }
    //         }}
    //     //image.src = _URL.createObjectURL(file);
    // });

    //
    // $scope.uploadFile = function(){
    //     var file = $scope.myFile;
    //     console.log('file is ' );
    //     console.dir(file);
    //     var uploadUrl = "/fileUpload";
    //     fileUpload.uploadFileToUrl(file, uploadUrl);
    // };

}


angular.module('appStudentForm')
    .controller('appFormController', ['$scope', 'appFormService', 'Upload', appFormController]);
