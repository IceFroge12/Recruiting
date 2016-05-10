<%@page session="true" %>
<html ng-app="recruiting">
<head lang="en">
    <title>Stateless Authentication Example</title>
    <script src="/frontend/ng-upload/angular.js"></script>
    <script src="/js/auth/controllers.js"></script>
</head>
<body ng-controller="AuthCtrl" ng-init="init()" style="padding: 10% 20%">
<label for="un">Email:</label><input id="un" type="text" ng-model="email"><br>
<label for="pw">Password:</label><input id="pw" type="password" ng-model="password"><br>
<button ng-click="login()">Login</button>
Token content: <pre>{{token | json}}</pre>
</div>
</body>
</html>