<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<html>
<head>
    <title>Login Page</title>
<head>
<body>
<h1>Spring Security Login Form (Database Authentication)</h1>
<div id="login">

    <h3>Login with Username and Password</h3>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>

    <form name='loginForm' id="loginForm" action="<c:url value='/login' />" method='POST' prependId = "false">
        <table>
            <tr>
                <td>Email:</td>
                <td><input type='text' name='email'></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type='password' name='password'/></td>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" href="/user" type="submit" value="submit"/></td>
            </tr>
        </table>
    </form>

    <a href="/google-auth">GoogleAuth</a>
</div>
</body>
</html>