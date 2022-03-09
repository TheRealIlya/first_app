<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
    </head>

    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <h2>Login page</h2>
        <p style="color: red;">${errorMessage}</p>
        <form method="POST" action="${pageContext.request.contextPath}/jsp/login">
            User Name
            <input type="text" name="username" id="username"/>
            <p>
                Password
                <input type="password" name="password" id="password"/>
            </p>
            <input type="submit" value= "Enter" />
        </form>
    </body>
</html>