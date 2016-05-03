<%--
  Created by IntelliJ IDEA.
  User: uakruk
  Date: 5/3/16
  Time: 7:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main page</title>

    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/jasny-bootstrap.min.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,400,300,600,700&subset=latin,cyrillic'
          rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="css/client.css">
</head>
<body>
<div id="includedHeader"></div>
<div id="leftNavStaff"></div>
<div id="main-block" class="col-md-10 col-md-offset-0 col-sm-9 col-sm-offset-3">
    <div class="col-md-12 col-sm-12 left-info-col">
        <div>
            <form>
                <h3>Change password</h3>

                <div class="row">
                    <input type="password" placeholder="old password">
                </div>
                <br>

                <div class="row">
                    <input type="password" placeholder="new password">
                </div>
                <br>

                <div class="row">
                    <input type="password" placeholder="new password">
                </div>
                <button type="submit" class="btn btn-info search-element btn-left">Confirm</button>
            </form>
        </div>
    </div>
</div>
<!-- footer -->
<div id="footer"></div>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/client.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/jasny-bootstrap.min.js"></script>
</body>
</html>
