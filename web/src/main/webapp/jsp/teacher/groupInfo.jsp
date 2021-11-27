<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Group info</title>
    </head>

    <body>
        <jsp:include page="../common/header.jsp"></jsp:include>
        <p style="color: red;">${errorMessage}</p>
            <table>
                <tr>
                    <td><b>Group:</b></td>
                    <td>${group.title}</td>
                </tr>
                <tr>
                    <td><b>Students:</td></b>
                    <td>${group.students}</td>
                </tr>
                <tr>
                    <td><b>Themes:</td></b>
                    <td>${group.themes}</td>
                </tr>
            </table>
        <p><a href="${pageContext.request.contextPath}/createGrade">Assess student</a></p>
        <p style="color: green;">${approveMessage}</p>
    </body>
</html>