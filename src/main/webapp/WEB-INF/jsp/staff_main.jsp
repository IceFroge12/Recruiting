<%--
  Created by IntelliJ IDEA.
  User: uakruk
  Date: 5/2/16
  Time: 12:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
    <%@ include file="staff_header.jsp" %>
    <%@ include file="staff_leftnav.jsp" %>

    <div id="main-block" class="col-md-10 col-md-offset-0 col-sm-9 col-sm-offset-3">
        <div class="col-md-12 col-sm-12 left-info-col">
            <div>
                <div class="info">
                    <h4>Deadlines for current recruitment:</h4>
                    <br>
                    <p>Registration deadline: <span id="registration-deadline"></span></p>
                    <br>
                    <p>Schedule time deadline: <span id="schedule-deadline"></span></p>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="staff_footer.jsp" %>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/client.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/jasny-bootstrap.min.js"></script>

</body>
</html>
