<%@page session="true" %>
<html>
<head>
    <title>Login Page</title>
    <script src="/js/jquery-1.12.3.min.js"></script>
    <script src="/js/authentication.js"></script>

<head>
<body>
<h1>Spring Security Login Form (Database Authentication)</h1>
<div id="login">

    <h3>Login with Username and Password</h3>

        <table>
            <tr>
                <td>Email:</td>
                <td><input id="username" type='text' name='email'></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input id="password" type='password' name='password'/></td>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" href="/user" type="submit" value="submit"/></td>
            </tr>
        </table>
    <button id="loginButton" type="submit">Login</button>

</div>
</body>
</html>