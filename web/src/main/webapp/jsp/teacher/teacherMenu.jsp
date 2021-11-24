<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Teacher Menu</title>
    </head>

    <body>
        <jsp:include page="../common/header.jsp"></jsp:include>
        <h2>Hello, teacher!</h2>
        <p>
        <a href="${pageContext.request.contextPath}/groupInfo">Get group info</a>
        </p>
    </body>
</html>