<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Group info</title>
    </head>

    <body>
        <jsp:include page="../common/header.jsp"></jsp:include>
        <h2>Group ${group}</h2>
        <p style="color: red;">${errorMessage}</p>

            <p style="color: green;">${approveMessage}</p>
    </body>
</html>