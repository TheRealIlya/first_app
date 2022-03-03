<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Menu</title>
    </head>

    <body>
        <jsp:include page="../common/header.jsp"></jsp:include>
        <h2>Admin Workspace</h2>
        <p>
        <a href="${pageContext.request.contextPath}/jsp/addTeacher">Add new teacher</a>
        </p>
        <p>
        <a href="${pageContext.request.contextPath}/jsp/avgSalary">Get average salary</a>
        </p>
        <p>
        <a href="${pageContext.request.contextPath}/jsp/addStudent">Add new student</a>
        </p>
    </body>
</html>