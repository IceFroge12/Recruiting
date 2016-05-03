<%--
  Created by IntelliJ IDEA.
  User: uakruk
  Date: 5/3/16
  Time: 7:36 PM
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
    <link rel="stylesheet" type="text/css" href="css/jquery.dataTables.min.css">
</head>
<body>
<div id="includedHeader"></div>
<div id="leftNavStaff"></div>

<div id="main-block" class="col-md-10 col-md-offset-0 col-sm-9 col-sm-offset-3">
    <div class="col-md-12 col-sm-12 left-info-col">
        <div>
            <button class="btn btn-sm btn-info search-element col-md-2" type="button" data-toggle="modal" data-target="#assignNew">
                Assign new student
            </button>
            <form class="form-search inline-display col-md-1 col-md-offset-8">
                <button class="btn btn-sm btn-info search-element" type="button" data-toggle="modal"
                        data-target="#filtration">
                    Filter table
                </button>
            </form>
            <table id="students_table" class="table table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Photo</th>
                    <th>Name</th>
                    <th>University</th>
                    <th>Course</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td>
                        <img src="img/chehov.png" width=40px>
                    </td>
                    <td><a href="studentProfile.html">Anton Pavlovich</a></td>
                    <td>KPI</td>
                    <td>1</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>
                        <img src="img/hohol.png" width=40px>
                    </td>
                    <td><a href="studentProfile.html">Nikolai Gogol</a></td>
                    <td>KNU</td>
                    <td>2</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>
                        <img src="img/lesya.png" width=40px>
                    </td>
                    <td><a href="studentProfile.html">Lesya Ukrainka</a></td>
                    <td>KNU</td>
                    <td>4</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- footer -->
<div id="footer"></div>

<div id="assignNew" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">Assign new student</h4>

                <div>
                    <form class="navbar-form navbar-left" role="search">
                        <p>Enter lastname</p>
                        <input id="candidateName" value="autocomplete">
                    </form>
                    <table id="founded_students" class="table table-hover">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Photo</th>
                            <th>Name</th>
                            <th>University</th>
                            <th>Course</th>
                            <th>Assign</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>
                                <img src="img/chehov.png" width=40px>
                            </td>
                            <td><a href="studentProfile.html">Anton Pavlovich</a></td>
                            <td>KPI</td>
                            <td>1</td>
                            <td>
                                <button class="btn btn-sm btn-info" type="button">
                                    Assign
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>
                                <img src="img/hohol.png" width=40px>
                            </td>
                            <td><a href="studentProfile.html">Nikolai Gogol</a></td>
                            <td>KNU</td>
                            <td>2</td>
                            <td>
                                <button class="btn btn-sm btn-info" type="button">
                                    Assign
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>
                                <img src="img/lesya.png" width=40px>
                            </td>
                            <td><a href="studentProfile.html">Lesya Ukrainka</a></td>
                            <td>KNU</td>
                            <td>4</td>
                            <td>
                                <button class="btn btn-sm btn-info" type="button">
                                    Assign
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="filtration" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">Filtration parameters</h4>

                <div>
                    <table class="table">
                        <tr>
                            <th>
                                Application form field
                            </th>
                            <th>
                                Restrictions
                            </th>
                        </tr>
                        <tr>
                            <td>
                                Status
                            </td>
                            <td>
                                <input type="checkbox" name="status" value="1">to job
                                <input type="checkbox" name="status" value="2">to general course
                                <input type="checkbox" name="status" value="3">to advanced course
                                <input type="checkbox" name="status" value="4">rejected
                            </td>
                        </tr>
                        <tr>
                            <td>
                                University
                            </td>
                            <td>
                                <input type="checkbox" name="iniv" value="1">KPI
                                <input type="checkbox" name="iniv" value="2">KNU
                                <input type="checkbox" name="iniv" value="3">NAU
                                <input type="checkbox" name="iniv" value="4">other
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Course
                            </td>
                            <td>
                                <input type="checkbox" name="course" value="1">1
                                <input type="checkbox" name="course" value="2">2
                                <input type="checkbox" name="course" value="3">3
                                <input type="checkbox" name="course" value="4">4
                            </td>
                        </tr>
                        <tr>
                            <td>
                                etc
                            </td>
                            <td>
                                etc
                            </td>
                        </tr>

                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-info" type="button" data-dismiss="modal">Filter</button>
            </div>
        </div>
    </div>
</div>

<script src='lib/jquery.min.js'></script>
<script src='lib/moment.min.js'></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script src='fullcalendar/fullcalendar.js'></script>
<script type="text/javascript" src="js/client.js"></script>
</body>
</html>