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
<%@ include file="adminmenupanel.jsp" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,400,300,600,700&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/css/client.css">
    <link rel="stylesheet" type="text/css" href="/css/jasny-bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/jquery.dataTables.min.css">
    <title>Student Management</title>
</head>

<div id="includedHeader"></div>
<div id="leftNav"></div>

<div id="main-block" class="col-md-10 col-md-offset-0 col-sm-9 col-sm-offset-3">
    <div class="col-md-12 col-sm-12 left-info-col">
        <div>
            <div class="row">
                <div class="col-md-2"><h6><i>Approved to work:20</i></h6></div>
                <div class="col-md-2"><h6><i>Approved to advanced:20</i></h6></div>
                <div class="col-md-2"><h6><i>Approved to general:80</i></h6></div>
            </div>
            <div class="col-md-5">
                <button type="button" class="btn btn-info btn-sm search-element">Confirm selection</button>
                <button type="button" class="btn btn-info btn-sm search-element" disabled>Announce results</button>
                <button type="button" class="btn btn-info btn-sm search-element" disabled>End of recruitment</button>
            </div>
            <form class="form-search inline-display col-md-2 col-md-offset-5">
                <button class="btn btn-sm btn-info search-element" type="button" data-toggle="modal"
                        data-target="#filtration">
                    Filter table
                </button>
            </form>
            <table id="students_table" class="display">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Photo</th>
                    <th>Name</th>
                    <th>Status</th>
                    <th>University</th>
                    <th>Course</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- footer -->
<div id="footer"></div>
<!-- modal dialog -->
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

<script src='/lib/jquery.min.js'></script>
<script type="text/javascript" src="/js/admin/adminstudentmanagement.js"></script>
<script src='/lib/moment.min.js'></script>
<script type="text/javascript" src="/js/util/jquery.dataTables.min.js"></script>
<script src='/fullcalendar/fullcalendar.js'></script>
<script type="text/javascript" src="/js/util/client.js"></script>
<script type="text/javascript" src="/js/util/bootstrap.js"></script>
</body>

</html>
