<%@page session="true" %>
<html ng-app="recruiting">
<head lang="en">
    <title>Stateless Authentication Example</title>
    <script src="/js/angular.js"></script>
    <script src="/js/controllers.js"></script>
</head>
<body ng-controller="AuthCtrl" ng-init="init()" style="padding: 10% 20%">
<div ng-hide="authenticated">
    Login as user/user or admin/admin:<br>
    <label for="un">Email:</label><input id="un" type="text" ng-model="email"><br>
    <label for="pw">Password:</label><input id="pw" type="password" ng-model="password"><br>
    <button ng-click="login()">Login</button>
</div>
<div ng-show="authenticated">
    Token content: <pre>{{token}}</pre>
</div>
</body>
</html>