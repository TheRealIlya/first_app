<html>
    <head>
        <meta charset="UTF-8">
        <title>Error page</title>
    </head>

    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <p style="color: red;">${errorMessage}</p>
    </body>
</html>