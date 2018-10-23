<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Schedule</title>
    <style>
        <%@include file="/css/list.css"%>
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

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
                    <table class="table">
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
                            <tr>
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
                        <tr style="background-color: #CBEEF4">
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
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>