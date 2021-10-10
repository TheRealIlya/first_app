<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
    </head>

    <body>
    <h2>Login page</h2>
    <form method="POST" action="${pageContext.request.contextPath}/login">
        User Name
        <input type="text" name="userName" value="" />
        <p>
            Password
            <input type="password" name="password" value="" />
        </p>
        <input type="submit" value= "Enter" />
        <a href="${pageContext.request.contextPath}/">HomePage</a>
    </form>

    </body>
</html>