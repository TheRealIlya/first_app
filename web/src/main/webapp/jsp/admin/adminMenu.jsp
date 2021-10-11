<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Menu</title>
    </head>

    <body>
        <jsp:include page="../common/header.jsp"></jsp:include>
        <h2>Admin Workspace</h2>

        <p><a href="${pageContext.request.contextPath}/home">Home Page</a></p>
        <p style="color: green;">${user}</p>
    </body>
</html>