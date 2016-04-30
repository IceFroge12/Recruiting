var app = angular.module('recruiting', []).factory('TokenStorage', function() {
	var storageKey = 'auth_token';
	return {		
		store : function(token) {
			return localStorage.setItem(storageKey, token);
		},
		retrieve : function() {
			return localStorage.getItem(storageKey);
		},
		clear : function() {
			return localStorage.removeItem(storageKey);
		}
	};
}).factory('TokenAuthInterceptor', function($q, TokenStorage) {
	return {
		request: function(config) {
			var authToken = TokenStorage.retrieve();
			if (authToken) {
				config.headers['X-AUTH-TOKEN'] = authToken;
			}
			return config;
		},
		responseError: function(error) {
			if (error.status === 401 || error.status === 403) {
				TokenStorage.clear();
			}
			return $q.reject(error);
		}
	};

}).config(function($httpProvider) {
	$httpProvider.interceptors.push('TokenAuthInterceptor');
});

app.controller('AuthCtrl', function ($scope, $http, $window) {
	$scope.authenticated = false;
	$scope.token; // For display purposes only
	
	// $scope.init = function () {
	// 	$http.get('/current').success(function (user) {
	// 		$scope.token = JSON.parse(atob(TokenStorage.retrieve().split('.')[0]));
	// 	});
	// };

	$scope.login = function () {
		// $http.post('/loginIn', { email: $scope.email, password: $scope.password }).success(function () {
         //    //$window.location.href = headers('redirectURL');
		// });
		
		$http({
			method : 'POST',
			url : '/loginIn',
			contentType: 'application/json',
			data : { email: $scope.email, password: $scope.password }
		})
	};

	$scope.logout = function () {
		TokenStorage.clear();	
		$scope.authenticated = false;
	};
});