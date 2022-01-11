<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Student Menu</title>
    </head>

    <body>
        <jsp:include page="../common/header.jsp"></jsp:include>
        <h2>Hello, student!</h2>
        <p><a href="${pageContext.request.contextPath}/jsp/checkGrades">Check grades</a></p>
    </body>
</html>