<%--
  Created by IntelliJ IDEA.
  User: dima
  Date: 23.04.16
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="adminheader.jsp" %>
<%@ include file="adminfooter.jsp" %>
<%--<%@ include file="adminmenupanel.jsp" %>--%>
<html>
<head>
    <script type="text/javascript" src="/js/util/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/util/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,400,300,600,700&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/css/client.css">
    <script type="text/javascript" src="/js/admin/adminstudentmanagement.js"></script>

    <title>Student Management</title>
</head>
<body>

<div id="main-container" class="container-fluid">
    <div class="container-fluid text-center">
        <div class="row">
            <div class="col-md-10 col-sm-12 left-info-col">
                <div class="row">
                    <div class="col-md-2"><h6><i>Approved to work:20</i></h6></div>
                    <div class="col-md-2"><h6><i>Approved to advanced:20</i></h6></div>
                    <div class="col-md-2"><h6><i>Approved to general:80</i></h6></div>
                </div>
                <div class="col-md-4">
                    <button type="button" class="btn btn-info btn-sm search-element">Confirm selection</button>
                    <button type="button" class="btn btn-info btn-sm search-element" disabled>Announce results</button>
                </div>
                <form class="form-search inline-display col-md-6 col-md-offset-2">
                    <input type="text" class="input-medium search-query">
                    <button type="submit" class="btn btn-sm btn-info search-element">Search</button>
                    <button type="button" class="btn btn-sm btn-info search-element">Extended Search</button>
                </form>
            </div>
        </div>

        <table id="employee-table" class="table table-hover ">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>E-mail</th>
                <th>Role</th>
                <th>Status</th>
                <th>Assigned students</th>
                <th>Edit</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>

</div>

</body>
<%--<script type="text/javascript" src="/js/client.js"></script>--%>

</html>
