<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<c:choose>
    <c:when test="${trains.size()=='0'}">
       No trains registered
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <td>Train number</td>
                <td>Number of seats</td>
            </tr>
        <c:forEach items="${trains}" var="train">
            <tr>
                <td>${train.number}</td>
                <td>${train.numberOfSeats}</td>
            </tr>
        </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>