<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ticket</title>
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
    <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false">
        Menu
    </button>
    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        <a class="dropdown-item" href="<c:url value="/"/>">Main page</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/schedule"/>">Schedule</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/tickets"/>">Tickets</a>
    </div>
    <form action="/logout" method="get">
        <input type="submit" value="Logout" class="login" role="button"/>
    </form>
</div>
<div class="main">
    <div class="container">
        <div class="row">
            <p>Ticket</p>
            <table class="table">
                <tr>
                    <td>Name</td>
                    <td>${ticket.passanger.name}</td>
                </tr>
                <tr>
                    <td>Surname</td>
                    <td>${ticket.passanger.surname}</td>
                </tr>
                <tr>
                    <td>Patronymic</td>
                    <td>${ticket.passanger.patronymic}</td>
                </tr>
                <tr>
                    <td>Date of birth</td>
                    <td>${ticket.passanger.dateOfBirth.toString().split(" ")[0]}</td>
                </tr>
                <tr>
                    <td>Departure station</td>
                    <td>${ticket.firstStation.stationName}</td>
                </tr>
                <tr>
                    <td>Arrival station</td>
                    <td>${ticket.lastStation.stationName}</td>
                </tr>
                <tr>
                    <td>Departure date</td>
                    <td>${ticket.departureDate.toString().split(" ")[0]}</td>
                </tr>
                <tr>
                    <td>Departure time</td>
                    <td>${ticket.departureTime.toString().split(" ")[1].substring(0,5)}</td>
                </tr>
                <tr>
                    <td>Arrival time</td>
                    <td>${ticket.arrivalTime.toString().split(" ")[1].substring(0,5)}</td>
                </tr>
                <tr>
                    <td>Journey time</td>
                    <td>${ticket.journeyTime.toString().split(" ")[1].substring(0,5)}</td>
                </tr>
                <tr>
                    <td>Seat</td>
                    <td>${ticket.seat}</td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>