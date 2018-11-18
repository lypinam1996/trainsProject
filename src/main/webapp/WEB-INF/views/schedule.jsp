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
    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>
    <script src="/js/rest.js"></script>
</head>
<body>
<div class="nav-item dropdown">
    <c:choose>
        <c:when test="${role.title.equals('WORKER')}">
            <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                Menu
            </button>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="<c:url value="/"/>">Main page</a>
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
            <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                Menu
            </button>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="<c:url value="/"/>">Main page</a>
            </div>
            <form action="/logout" method="get">
                <input type="submit" value="Logout" class="login"/>
            </form>
        </c:when>
        <c:otherwise>
            <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                Menu
            </button>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="<c:url value="/"/>">Main page</a>
            </div>
            <form action="/login" method="get">
                <input type="submit" value="Login" class="login"/>
            </form>
        </c:otherwise>
    </c:choose>
    <form:form action="/findStation" modelAttribute="station"
               method="POST" class="form-inline formSearch">
        <div class="form-group">
            <label for="idStation">Station name</label>
            <form:select path="idStation" class="input">
                <c:forEach var="item" items="${stations}">
                    <option class="inp" selected="selected"
                            value="${item.idStation}">${item.stationName}</option>
                </c:forEach>
            </form:select>
        </div>
        <div class="form-group">
            <button type="submit" class="b">Search</button>
        </div>
    </form:form>
</div>
<div class="main">
    <div class="container">
        <div class="row">
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
                        </c:when>
                    </c:choose>
                </tr>
                <c:forEach items="${schedules}" var="schedule">
                    <tr class="change">
                        <td>${schedule.train.number}</td>
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
                        </c:when>
                    </c:choose>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>