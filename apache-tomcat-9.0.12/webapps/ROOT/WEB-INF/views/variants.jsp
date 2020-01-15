<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Variants</title>
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
    <form action="/" method="get">
        <input type="submit" value="Back" class="login" style="margin-top: 10px"/>
    </form>
</div>
<div class="main">
    <div class="container">
        <div class="row">
            <c:choose>
                <c:when test="${tickets.size()=='0'}">
                    <p>No trains registered</p>
                </c:when>
                <c:otherwise>
                    <p>Trains</p>
                    <table class="table">
                        <tr class="firstTR" style="background-color: #bf4031;">
                            <td>Train number</td>
                            <td>Departure station</td>
                            <td>Arrival station</td>
                            <td>Departure time</td>
                            <td>Arrival time</td>
                            <td>Journey time</td>
                            <c:choose>
                                <c:when test="${role.title.equals('USER')}">
                                    <td></td>
                                </c:when>
                            </c:choose>
                        </tr>
                        <c:forEach items="${tickets}" var="ticket">
                            <tr>
                                <td>${ticket.schedule.train.number}</td>
                                <td>${ticket.firstStation.stationName}</td>
                                <td>${ticket.lastStation.stationName}</td>
                                <td><fmt:formatDate value="${ ticket.departureTime}" pattern="HH:mm"/></td>
                                <td><fmt:formatDate value="${ ticket.arrivalTime}" pattern="HH:mm"/></td>
                                <td><fmt:formatDate value="${ ticket.journeyTime}" pattern="HH:mm"/></td>
                                <c:choose>
                                    <c:when test="${role.title.equals('USER')}">
                                        <td><a href="/chooseTicket/${ticket.idVariant}">
                                            Choose
                                        </a></td>
                                    </c:when>
                                </c:choose>
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