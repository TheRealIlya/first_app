<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Student</title>
    </head>

    <body>
        <jsp:include page="../common/header.jsp"></jsp:include>
        <h2>Adding new student. Enter values:</h2>
        <p style="color: red;">${errorMessage}</p>
        <form method="POST" action="${pageContext.request.contextPath}/addStudent">
            <table>
                <tr>
                    <td><b>Login</b></td>
                    <td><input type="text" name="login" value="" /></td>
                </tr>
                <tr>
                    <td><b>Password</td></b>
                    <td><input type="password" name="password" value="" /></td>
                </tr>
                <tr>
                    <td><b>Name</td></b>
                    <td><input type="text" name="userName" value="" /></td>
                </tr>
                <tr>
                    <td><b>Age</td></b>
                    <td><input type="text" name="age" value="" /></td>
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