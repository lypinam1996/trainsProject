<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../../img/favicon.ico" rel="shortcut icon" type="image/x-icon" />
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
                            <td>Branch name</td>
                            <td>Stations and its time from previous station</td>
                            <td></td>
                        </tr>
                        <c:forEach items="${branches}" var="branch">
                            <tr>
                                <td>${branch.title}</td>
                                <td>
                                    <select class="inp" style="width: 200px;">
                                        <c:forEach var="item" items="${branch.detailedInf}">
                                            <option>${item.station.stationName} <fmt:formatDate
                                                    value="${inf.timeFromPrevious}" pattern="HH:mm"/></option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><a href="/createSchedule/${branch.idBranchLine}">
                                    Choose
                                </a></td>
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