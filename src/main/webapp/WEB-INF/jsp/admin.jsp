<%--
  Created by IntelliJ IDEA.
  User: dima
  Date: 14.04.16
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script type="text/javascript" src="/js/admin.js"></script>
</head>
<body>
    <a id="showAllEmployee">Staff managment</a>

    FORM ADD EMPLOYEE
    <input type="text" id="firstName">
    <input type="text" id="secondName">
    <input type="text" id="lastName">
    <input type="text" id="email">

    <input type="checkbox" id="tech">
    <input type="checkbox" id="soft">
    <input type="checkbox" id="admin">

    <button id="addEmployee" type="button" name="Add">Add Employee</button>

    <table id="employee-table"></table>


    FORM EDIT EMPLOYEE
    ​
    <table>
        <tr>
            <td>6</td>
            <td>Shon</td>
            <td class="userEmail">shon@me.com</td>
            <td>Tech</td>
            <td>
                <button class="btn btn-info" type="button">Delegate</button>
            </td>
            <td>
                <button class="btn btn-info" type="button" data-toggle="modal" data-target="#assignedList">Show list
                </button>
            </td>
            <td>
                <button class="btn btn-link editEmployee" type="button" data-toggle="modal">
                    <span class="glyphicon glyphicon-edit">Edit Employee</span>
                </button>
            </td>
        </tr>
    </table>


    <input type="text" id="firstNameEdit">
    <input type="text" id="secondNameEdit">
    <input type="text" id="lastNameEdit">
    <input type="text" id="emailEdit">

    <input type="checkbox" id="techEdit">
    <input type="checkbox" id="softEdit">
    <input type="checkbox" id="adminEdit">


    <button id="saveChanges" type="button">Save</button>

</body>
</html>
