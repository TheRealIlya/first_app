<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Menu</title>
    </head>

    <body>
        <jsp:include page="../common/header.jsp"></jsp:include>
        <h2>Admin Workspace</h2>

        <p><a href="${pageContext.request.contextPath}/">Home Page</a></p>
        <p style="color: green;">${loginedUser}</p>
    </body>
</html>