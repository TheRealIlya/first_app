<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Home Page</title>
    </head>

    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <c:set var="adminRole" value="ADMIN"/>
        <c:choose>
            <c:when test="${user.role.toString().equals(adminRole)}">
                <jsp:forward page="../admin/adminMenu.jsp"/>
            </c:when>
            <c:when test="${role.equals(TEACHER)}">
                //FORTEACHER//
            </c:when>
            <c:otherwise>
                <h2><p style="color: red;">This is impossible o_O</p></h2>
            </c:otherwise>
        </c:choose>
    </body>
</html>