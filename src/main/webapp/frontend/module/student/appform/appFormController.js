/**
 * Created by dima on 02.05.16.
 */

function appFormController($scope,ngToast, $http, appFormService) {
    appFormService.loadAppFormData().then(function success(data) {
		$scope.id = data.id;
        $scope.questions = data.questions;
        $scope.user = data.user;
        $scope.status = data.status;
        $scope.data = data;

    }, function error() {
        console.log("error");
    });

    $scope.submit = function () {
        var req =  $http({
            method : 'POST',
            url : '/student/saveApplicationForm',
            contentType: 'application/json',
            data : {
                status : $scope.data.status,
                user : $scope.data.user,
                questions : $scope.data.questions
            }
        });
       var response; 
       req.success(function(data) {
			console.log(data);
			$scope.resultMessage =  data;
		});
       return response;
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



    $scope.exportAppForm = function(){
        var config = {
            method: 'GET',
            url: "/student/appform"+ $scope.id,
            headers: {
                'Accept': 'application/pdf'
            }
        };
        $http(config)
            .success(function(){
                window.location = "/student/appform"+ $scope.id;
            })
            .error(function(){
                var myToastMsg = ngToast.warning({
                    content: 'Error exporting Application Form ',
                    timeout: 5000,  //TODO : Change color, position
                    horizontalPosition: 'center',
                    verticalPosition: 'bottom',
                    dismissOnClick: true,
                    combineDuplications: true,
                    maxNumber: 2
                });
            });
    };

}



angular.module('appStudentForm')
    .controller('appFormController', ['$scope','ngToast','$http','appFormService', appFormController]);
