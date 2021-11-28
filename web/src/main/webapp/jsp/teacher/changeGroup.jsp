<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Change group</title>
    </head>

    <body>
        <jsp:include page="../common/header.jsp"></jsp:include>
        <h3>Enter new group&#x60s title</h3>
        <h4>(empty if you just wanna leave from the current group)</h4>
        <p style="color: red;">${errorMessage}</p>
        <form method="POST" action="${pageContext.request.contextPath}/changeGroup">
            <table>
                <tr>
                    <td><b>Group title</b></td>
                    <td><input type="text" name="title" value="" /></td>
                </tr>
                <tr>
                    <td />
                    <td><input type="submit" value= "Enter" /></td>
                </tr>
            </table>
        </form>
    </body>
</html>