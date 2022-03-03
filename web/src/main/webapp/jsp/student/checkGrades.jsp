<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Check grades</title>
        <style type="text/css">
        table {
            border-collapse: collapse;
        }
        td {
            border: 1px solid grey;
        }
        </style>
    </head>

    <body>
        <jsp:include page="../common/header.jsp"></jsp:include>
        <p />
        <h3>Your marks:</h3>
        <table>
            <c:forEach var="gr" items="${user.getGroups()}">
                <tr>
                <td rowspan="${gr.getThemes().size()}">${gr.getTitle()}</td>
                <c:forEach var="theme" items="${gr.getThemes()}">
                    <td>${theme}</td>
                    <c:forEach var="grade" items="${theme.getGrades()}">
                        <c:if test="${grade.getStudent().equals(user) && grade.getGroup().equals(gr)}">
                            <td>${grade}</td>
                        </c:if>
                    </c:forEach>
                    </tr>
                    <tr>
                </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>