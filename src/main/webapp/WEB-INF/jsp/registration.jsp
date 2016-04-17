<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: dima
  Date: 12.04.16
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Registration</h1>
<form:form method="POST" commandName="user" action="registration/signup">
    <table>
        <tr>
            <td><form:input path="firstName" /></td>
        </tr>
        <tr>
            <td><form:input path="secondName" /></td>
        </tr>
        <tr>
            <td><form:input path="lastName" /></td>
        </tr>
        <tr>
            <td><form:input path="email" /></td>
        </tr>
        <tr>
            <td><form:input path="password" /></td>
        </tr>
        <tr>
            <td colspan="3"><input type="submit" value="Sign Up"/></td>
        </tr>
    </table>
</form:form>

</body>
</html>
