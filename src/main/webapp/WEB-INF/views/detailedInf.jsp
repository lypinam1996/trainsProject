<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Detailed information</title>
    <style>
        <%@include file="/css/list.css"%>
    </style>
    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">

    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>
</head>
<body>
<div class="nav-item dropdown">
    <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false">
        Menu
    </button>
    <form action="/logout" method="get">
        <input type="submit" value="Logout" class="login"/>
    </form>
    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        <a class="dropdown-item" href="<c:url value="/"/>">Main page</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/stations"/>">Stations</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/schedule"/>">Schedule</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/trains"/>">Trains</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/branches"/>">Back</a>
    </div>
</div>
<div class="main">
    <div class="container">
        <div class="row">
            <c:choose>
                <c:when test="${detailedInf.size()=='0'}">
                    <p>No detailed information registered</p>
                </c:when>
                <c:otherwise>
                    <p>Branches</p>
                    <table class="table">
                        <tr class="firstTR" style="background-color: #bf4031;">
                            <td>Station serial number</td>
                            <td>Station name</td>
                            <td>Time from previous station</td>
                        </tr>
                        <c:forEach items="${detailedInf}" var="inf">
                            <tr>
                                <td>${inf.stationSerialNumber}</td>
                                <td>${inf.station.stationName}</td>
                                <td><fmt:formatDate value="${inf.timeFromPrevious}" pattern="HH:mm"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>