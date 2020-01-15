<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Schedule</title>
    <style>
        <%@include file="/css/list.css"%>
    </style>
    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/sunny/jquery-ui.css">
    <link href="../../img/favicon.ico" rel="shortcut icon" type="image/x-icon"/>
    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>

</head>
<body>
<div class="nav-item dropdown">
    <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false">
        Menu
    </button>
    <c:choose>
        <c:when test="${role.title.equals('WORKER')}">
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="<c:url value="/home"/>">Main page</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="<c:url value="/stations"/>">Stations</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="<c:url value="/branches"/>">Branches</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="<c:url value="/trains"/>">Trains</a>
                <div class="dropdown-divider"></div>
            </div>
            <form action="/logout" method="get">
                <input type="submit" value="Logout" class="login"/>
            </form>
        </c:when>
        <c:when test="${role.title.equals('USER')}">
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="<c:url value="/home"/>">Main page</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="<c:url value="/trackIndicator"/>">Track indicator</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="<c:url value="/tickets"/>">Tickets</a>
            </div>
            <form action="/logout" method="get">
                <input type="submit" value="Logout" class="login"/>
            </form>
        </c:when>
        <c:otherwise>
            </button>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="<c:url value="/home"/>">Main page</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="<c:url value="/trackIndicator"/>">Track indicator</a>
            </div>
            <a class="link nav-link dropdown-toggle" href="<c:url value="/login"/>">Login</a>
        </c:otherwise>
    </c:choose>
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
            <c:when test="${schedules.size()=='0'}">
                <p>No trains registered</p>
            </c:when>
            <c:otherwise>
            <p>Schedule</p>
            <table class="table" id="table">
                <tr class="firstTR" style="background-color: #bf4031;">
                    <td>Train number</td>
                    <td>Departure station</td>
                    <td>Arrival station</td>
                    <td>Departure time</td>
                    <c:choose>
                        <c:when test="${role.title.equals('WORKER')}">
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </c:when>
                    </c:choose>
                </tr>
                <c:forEach items="${schedules}" var="schedule">
                    <tr class="change">
                        <td id="${schedule.idSchedule}">${schedule.train.number}</td>
                        <td>${schedule.firstStation.stationName}</td>
                        <td>${schedule.lastStation.stationName}</td>
                        <td><fmt:formatDate value="${schedule.departureTime}" pattern="HH:mm"/></td>
                        <c:choose>
                            <c:when test="${role.title.equals('WORKER')}">
                                <td><a href="/updateSchedule/${schedule.idSchedule}">
                                    Edit
                                </a></td>
                                <td><a href="/deleteSchedule/${schedule.idSchedule}">
                                    Delete
                                </a></td>
                                <td><a href="/seeTickets/${schedule.idSchedule}">
                                    See passangers
                                </a></td>
                                <c:choose>
                                    <c:when test="${schedule.prohibitPurchase==null}">
                                        <td><a href="/prohibitPurchase/${schedule.idSchedule}">
                                            Prohibit purchase
                                        </a></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><a href="/openPurchase/${schedule.idSchedule}">
                                            Open purchase
                                        </a></td>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                        </c:choose>
                    </tr>
                </c:forEach>
                </c:otherwise>
                </c:choose>
                <tr style="background-color: #CBEEF4" class="change">
                    <c:choose>
                        <c:when test="${role.title.equals('WORKER')}">
                            <td>
                                <form action="/createSchedule" method="get">
                                    <input type="submit" value="Add train"
                                           style="margin-top: 1%; margin-left: 1%"
                                           class="login"/>
                                </form>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td></td>
                        </c:otherwise>
                    </c:choose>
                    <td></td>
                    <td></td>
                    <td></td>
                    <c:choose>
                        <c:when test="${role.title.equals('WORKER')}">
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </c:when>
                    </c:choose>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>