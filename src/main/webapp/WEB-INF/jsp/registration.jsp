<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: dima
  Date: 12.04.16
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form:form method="POST" commandName="user" action="registration/signup">
    <table>
        <tr>
            FirstName <td><form:input path="firstName" /></td>
        </tr>
        <tr>
            SecondName <td><form:input path="secondName" /></td>
        </tr>
        <tr>
            LastName <td><form:input path="lastName" /></td>
        </tr>
        <tr>
            Email <td><form:input path="email" /></td>
        </tr>
        <tr>
            Password <td><form:input path="password" /></td>
        </tr>
        <tr>
            <td colspan="3"><input type="submit" value="Sign Up"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>
