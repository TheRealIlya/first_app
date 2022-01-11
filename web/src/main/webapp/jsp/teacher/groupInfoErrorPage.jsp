<html>
    <head>
        <meta charset="UTF-8">
        <title>Error page</title>
    </head>

    <body>
        <jsp:include page="../common/header.jsp"></jsp:include>
        <p style="color: red;">${errorMessage}</p>
        <p><a href="${pageContext.request.contextPath}/jsp/changeGroup" style="color: red;">Change group</a></p>
    </body>
</html>