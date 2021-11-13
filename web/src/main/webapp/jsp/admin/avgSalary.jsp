<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Average Salary</title>
    </head>

    <body>
        <jsp:include page="../common/header.jsp"></jsp:include>
        <h2>Enter data to get average salary of teacher:</h2>
        <p style="color: red;">${errorMessage}</p>
        <form method="POST" action="${pageContext.request.contextPath}/avgSalary">
            <table>
                <tr>
                    <td><b>Teacher&#x60s login</b></td>
                    <td><input type="text" name="login" value="" /></td>
                </tr>
                <tr>
                    <td><b>First month</td></b>
                    <td><input type="text" name="firstMonth" value="" /></td>
                </tr>
                <tr>
                    <td><b>Last month</td></b>
                    <td><input type="text" name="lastMonth" value="" /></td>
                </tr>
                <tr>
                    <td />
                    <td><input type="submit" value= "Enter" /></td>
                </tr>
            </table>
            <h2 style="color: green;">${result}</h2>
        </form>
    </body>
</html>