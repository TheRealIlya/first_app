<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Create grade</title>
    </head>

    <body>
        <jsp:include page="../common/header.jsp"></jsp:include>
        <h2>Assess student in your group</h2>
        <p style="color: red;">${errorMessage}</p>
        <form method="POST" action="${pageContext.request.contextPath}/createGrade">
            <table>
                <tr>
                    <td><b>Student&#x60s login</b></td>
                    <td><input type="text" name="studentLogin" value="" /></td>
                </tr>
                <tr>
                    <td><b>Theme</td></b>
                    <td><input type="text" name="themeString" value="" /></td>
                </tr>
                <tr>
                    <td><b>Grade</td></b>
                    <td><input type="text" name="gradeString" value="" /></td>
                </tr>
                <tr>
                    <td />
                    <td><input type="submit" value= "Enter" /></td>
                </tr>
            </table>
            <p style="color: green;">${approveMessage}</p>
        </form>
    </body>
</html>