<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Schedule</title>
    <style>
        <%@include file="/css/list.css"%>
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

</head>
<body>
<div class="nav-item dropdown">
    <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        Menu
    </button>
    <c:choose>
        <c:when test="${auth.equals('anonymousUser')}">
            <form action="/login" method="get">
                <input type="submit" value="Login"class="login"/>
            </form>
        </c:when>
        <c:otherwise>
            <form action="/logout" method="get">
                <input type="submit" value="Logout"class="login"/>
            </form>
        </c:otherwise>
    </c:choose>
    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        <a class="dropdown-item" href="<c:url value="/"/>">Main page</a>
        <div class="dropdown-divider"></div>
    </div>
</div>
<div class="main">
    <div class="container">
        <div class="row">
            <p>Trains</p>
            <c:choose>
                <c:when test="${schedules.size()=='0'}">
                    <p>No trains registered</p>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <tr class="firstTR" style="background-color: #bf4031;">
                            <td>Train number</td>
                            <td>Departure station</td>
                            <td>Arrival station</td>
                            <td>Departure time</td>
                        </tr>
                        <c:forEach items="${schedules}" var="schedule">
                            <tr>
                                <td>${schedule.train.number}</td>
                                <td>${schedule.firstStation.stationName}</td>
                                <td>${schedule.lastStation.stationName}</td>
                                <td>${schedule.departureTime.getHours()}:${schedule.departureTime.getMinutes()}</td>
                                <td><a href="/updateSchedule/${schedule.idSchedule}">
                                    Edit
                                </a></td>
                                <td><a href="/deleteSchedule/${schedule.idSchedule}">
                                    Delete
                                </a></td>
                            </tr>
                        </c:forEach>
                        <tr style="background-color: #CBEEF4">
                            <td><form action="/createSchedule" method="get">
                                <input type="submit" value="Add new train in schedule" style="margin-top: 1%; margin-left: 1%" class="login"/>
                            </form></td>
                            <td></td>
                            <td></td>
                        </tr>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>