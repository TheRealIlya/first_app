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
        <form method="POST" action="${pageContext.request.contextPath}/login">
            User Name
            <input type="text" name="userName" value="" />
            <p>
                Password
                <input type="password" name="password" value="" />
            </p>
            <input type="submit" value= "Enter" />
        </form>
    </body>
</html>