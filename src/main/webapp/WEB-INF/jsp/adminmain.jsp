<%--
  Created by IntelliJ IDEA.
  User: dima
  Date: 23.04.16
  Time: 9:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="adminheader.jsp" %>
<%@ include file="adminfooter.jsp" %>
<%@ include file="adminmenupanel.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main page</title>
    <script type="text/javascript" src="/js/util/jquery.js"></script>
    <script type="text/javascript" src="/js/admin/adminmain.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,400,300,600,700&subset=latin,cyrillic'
          rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/css/client.css">

</head>
<body>

<div id="main-container" class="container-fluid">
    <div class="container-fluid text-center">
        <div class="col-md-10 col-sm-12 left-info-col">
            <h3>Welcome, dear Admin!</h3>
            <div class="row placeholders">
                <div class="col-xs-6 col-sm-3 placeholder">
                    <h1 class="title1">2000</h1>
                    <h4>New registrations</h4>
                    <span class="text-muted">For the last week</span>
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <h1 class="title2" id="endregistration"></h1>
                    <h4>Days</h4>
                    <span class="text-muted">To registration ending</span>
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <h1 class="title1" id="startinterview"></h1>
                    <h4>Days</h4>
                    <span class="text-muted">To the interviews start</span>
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <h1 class="title2" id="endrecruiment"></h1>
                    <h4>Days</h4>
                    <span class="text-muted">To the end of recruiment</span>
                </div>
            </div>
            <br><br>
            <div class="info">
                <h4>Deadlines for current recruitment:</h4>
                <br>
                <p>Registration deadline: <span id="registration-deadline"><span></p>
                <br>
                <p>Choosing schedule time deadline: <span id="schedule-deadline"><span></p>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/util/client.js"></script>
<script type="text/javascript" src="/js/util/bootstrap.js"></script>
</div>
</body>
</html>
