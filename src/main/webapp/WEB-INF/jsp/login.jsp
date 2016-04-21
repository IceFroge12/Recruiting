<%@page session="true" %>
<html>
<head>
    <title>Login Page</title>
<head>
<body>
<h1>Spring Security Login Form (Database Authentication)</h1>
<div id="login">

    <h3>Login with Username and Password</h3>



    <form name='loginForm' id="loginForm" action="login" method='POST' prependId = "false">
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

</div>
</body>
</html>