<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Variants</title>
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
    <input type="button" class="login" value="Login" href="<c:url value="/"/>"/>
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
                <c:when test="${variants.size()=='0'}">
                    No trains registered
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <tr class="firstTR" style="background-color: #bf4031;">
                            <td>Train number</td>
                            <td>Departure station</td>
                            <td>Arrival station</td>
                            <td>Departure time</td>
                            <td>Arrival time</td>
                            <td>Journey time</td>
                        </tr>
                        <c:forEach items="${tickets}" var="ticket">
                            <tr>
                                <td>${ticket.schedule.train.number}</td>
                                <td>${ticket.firstStation.stationName}</td>
                                <td>${ticket.lastStation.stationName}</td>
                                <td>${ticket.departureTime.getHours()}:${ticket.departureTime.getMinutes()}</td>
                                <td>${ticket.arrivalTime.getHours()}:${ticket.arrivalTime.getMinutes()}</td>
                                <td>${ticket.journeyTime.getHours()}:${ticket.journeyTime.getMinutes()}</td>
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