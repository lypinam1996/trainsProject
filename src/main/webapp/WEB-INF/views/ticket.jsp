<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ticket</title>
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
    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        <a class="dropdown-item" href="<c:url value="/"/>">Main page</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/schedule"/>">Schedule</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/tickets"/>">Tickets</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/trackIndicator"/>">Track indicator</a>
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
                    <td><fmt:formatDate value="${ ticket.passanger.dateOfBirth}" pattern="dd.MM.yyyy"/></td>
                </tr>
                <tr>
                    <td>Train number</td>
                    <td>${ticket.schedule.train.number}</td>
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
                    <td><fmt:formatDate value="${ ticket.departureDate}" pattern="dd.MM.yyyy"/></td>
                </tr>
                <tr>
                    <td>Departure time</td>
                    <td><fmt:formatDate value="${ ticket.departureTime}" pattern="HH:mm"/></td>
                </tr>
                <tr>
                    <td>Arrival time</td>
                    <td><fmt:formatDate value="${ ticket.arrivalTime}" pattern="HH:mm"/></td>
                </tr>
                <tr>
                    <td>Journey time</td>
                    <td><fmt:formatDate value="${ ticket.journeyTime}" pattern="HH:mm"/></td>
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