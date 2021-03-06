<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Stations</title>
    <style>
        <%@include file="/css/list.css"%>
    </style>
    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">
    <link href="../../img/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>
</head>
<body>
<div class="nav-item dropdown">
    <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false">
        Menu
    </button>
    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        <a class="dropdown-item" href="<c:url value="/home"/>">Main page</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/branches"/>">Branches</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/schedule"/>">Schedule</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/trains"/>">Trains</a>
        <div class="dropdown-divider"></div>
    </div>
    <a class="link nav-link dropdown-toggle" href="<c:url value="/logout"/>">Logout</a>
</div>
<div class="main">
    <div class="container">
        <div class="row">
            <c:choose>
                <c:when test="${errors.size()!=0}">
                    <div class="errors">
                        <c:forEach items="${errors}" var="error">
                            <div>${error}</div>
                        </c:forEach>
                    </div>
                </c:when>
            </c:choose>
            <c:choose>
            <c:when test="${stations.size()=='0'}">
                <p>No stations registered</p>
            </c:when>
            <c:otherwise>
            <p>Stations</p>
            <table class="table">
                <tr class="firstTR" style="background-color: #bf4031;">
                    <td>Station name</td>
                    <td></td>
                    <td></td>
                </tr>
                <c:forEach items="${stations}" var="station">
                    <tr>
                        <td>${station.stationName}</td>
                        <td><a href="/updateStation/${station.idStation}">
                            Edit
                        </a></td>
                        <td><a href="/deleteStation/${station.idStation}">
                            Delete
                        </a></td>
                    </tr>
                </c:forEach>
                </c:otherwise>
                </c:choose>
                <tr style="background-color: #CBEEF4">
                    <td>
                        <form action="/createStation" method="get">
                            <input type="submit" value="Add new station" style="margin-top: 1%; margin-left: 1%"
                                   class="login"/>
                        </form>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
            </table>

        </div>
    </div>
</div>
</body>
</html>