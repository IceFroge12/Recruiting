<%--
  Created by IntelliJ IDEA.
  User: dima
  Date: 24.04.16
  Time: 9:16
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="adminheader.jsp" %>
<%@ include file="adminfooter.jsp" %>
<%@ include file="adminmenupanel.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,400,300,600,700&subset=latin,cyrillic'
          rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/css/client.css">
    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript" src="/js/bootstrap.js"></script>
    <script type="text/javascript" src="/js/adminchangepassword.js"></script>

</head>
<body>
<div id="main-container" class="container-fluid">
    <div class="container-fluid text-center">
        <div class="row">
            <div class="col-md-10 col-sm-12 left-info-col">
                <form>
                    <h3>Change password</h3>
                    <div class="row">
                        <input id="oldpassword" type="password" placeholder="old password">
                    </div>
                    <br>
                    <div class="row">
                        <input id="newpassword" type="password" placeholder="new password">
                    </div>
                    <br>
                    <div class="row">
                        <input id="confirmnewpassword" type="password" placeholder="new password">
                    </div>
                    <button type="submit" id="savePassword" class="btn btn-info search-element btn-left">Confirm</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>

</html>
