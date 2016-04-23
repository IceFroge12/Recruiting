<%--
  Created by IntelliJ IDEA.
  User: dima
  Date: 23.04.16
  Time: 9:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="adminheader.jsp" %>
<%@ include file="adminfooter.jsp" %>
<%@ include file="adminmenupanel.jsp" %>
<html>
<head>
    <title>StaffManagement</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,400,300,600,700&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/css/client.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script type="text/javascript" src="/js/admin.js"></script>
    <script type="text/javascript" src="/js/jquery-ui.min.js"></script>
</head>
<body>
<div id="main-container" class="container-fluid">
    <div class="container-fluid text-center">
            <div class="col-md-10 col-sm-12 left-info-col">
                <form class="form-search inline-display col-md-6 col-md-offset-6">
                    <input type="text" class="input-medium search-query">
                    <button type="submit" class="btn btn-sm btn-info search-element">Search</button>
                    <button type="button" class="btn btn-sm btn-info search-element">Extended Search</button>
                </form>
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
                <button class="btn btn-info btn-sm right-align" type="button" data-toggle="modal"
                        data-target="#addEmployee">Add employee
                </button>
            </div>
    </div>

    <!-- modal dialog -->
    <div id="editEmployee" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" type="button" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove"></span></button>
                    <h4 class="modal-title">Edit employee</h4>
                    <div>
                        <table class="table">
                            <tr>
                                <td> Name</td>
                                <td><input type="text" value="Vasya Pupkin"></td>
                            </tr>
                            <tr>
                                <td> Email</td>
                                <td><input type="text" value="pupkin@me.com"></td>
                            </tr>
                            <tr>
                                <td> Role</td>
                                <td>
                                    <input type="checkbox" id="inlineCheckbox1" value="option1"> Tech </input>
                                    <input type="checkbox" id="inlineCheckbox2" value="option2" checked> Soft </input>
                                    <input type="checkbox" id="inlineCheckbox3" value="option3"> Administrator </input>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" type="button" data-dismiss="modal">Save changes</button>
                </div>
            </div>
        </div>
    </div>

    <div id="addEmployee" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" type="button" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove"></span></button>
                    <h4 class="modal-title">New employee</h4>
                    <div>
                        <table class="table">
                            <tr>
                                <td>First Name</td>
                                <td><input id="firstName" type="text"></td>
                            </tr>
                            <tr>
                                <td>Second Name</td>
                                <td><input id="secondName" type="text"></td>
                            </tr>
                            <tr>
                                <td>Last Name</td>
                                <td><input id="lastName" type="text"></td>
                            </tr>
                            <tr>
                                <td> Email</td>
                                <td><input id="email" type="text"></td>
                            </tr>
                            <tr>
                                <td> Role</td>
                                <td>
                                    <input type="checkbox" id="tech" value="option1"> Tech </input>
                                    <input type="checkbox" id="soft" value="option2"> Soft </input>
                                    <input type="checkbox" id="admin" value="option3"> Administrator </input>
                                </td>
                            </tr>

                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" id="saveEmployee" type="button" data-dismiss="modal">Add</button>
                </div>
            </div>
        </div>
    </div>

    <div id="assignedList" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" type="button" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove"></span></button>
                    <h4 class="modal-title">Assigned candidates</h4>
                    <div>
                        <table class="table">
                            <thead>
                            <th>Name</th>
                            <th>Rated</th>
                            </thead>
                            <tbody>
                            <tr>
                                <td><a href="#">Ilya Muromets</td>
                                <td>+</td>
                            </tr>
                            <tr>
                                <td><a href="#">Dobrynya Nikitich</td>
                                <td>-</td>
                            </tr>
                            <tr>
                                <td><a href="#">Alyosha Popovich</td>
                                <td>+</td>
                            </tr>
                            <tr>
                                <td><a href="#">Svyatogor</td>
                                <td>+</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" type="button" data-dismiss="modal">Ok</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript" src="/js/client.js"></script>
    <script type="text/javascript" src="/js/bootstrap.js"></script>
</body>
</html>
