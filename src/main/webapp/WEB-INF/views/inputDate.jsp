<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Input date</title>
    <style>
        <%@include file="/css/form2.css"%>
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body style="background-color: #008ca5">
<%--<div class="nav-item dropdown">--%>
    <%--<form action="/" method="get">--%>
        <%--<input type="submit" value="Back" class="backBtn"/>--%>
    <%--</form>--%>
    <%--<form action="/logout" method="get" style="margin-top: -35px">--%>
        <%--<input type="submit" value="Logout" class="login"/>--%>
    <%--</form>--%>
<%--</div>--%>
<div class="container" style="  width: 50%">
    <div class="row">
        <div class="col-md-12">
            <form:form action="/chooseTicket" modelAttribute="ticket"
                       method="POST">
                <c:choose>
                    <c:when test="${!error.isEmpty()}">
                        <div class="errors">${error}</div>
                    </c:when>
                </c:choose>
                <div class="form-group">
                    <label  for="departureDate">Departure date</label>
                    <form:input class="form-control inp" type="date" path="departureDate"/>
                </div>
                <div class="form-group">
                    <label  for="passanger.name">Departure date</label>
                    <form:input class="form-control inp"  path="passanger.name"/>
                </div>
                <div class="form-group">
                    <label  for="passanger.surname">Departure date</label>
                    <form:input class="form-control inp"  path="passanger.surname"/>
                </div>
                <div class="form-group">
                    <label  for="passanger.patronymic">Departure date</label>
                    <form:input class="form-control inp"  path="passanger.patronymic"/>
                </div>
                <div class="form-group">
                    <label  for="passanger.dateOfBirth">Departure date</label>
                    <form:input class="form-control inp" type="date" path="passanger.dateOfBirth"/>
                </div>
                <form:input  type="hidden" path="idTicket"/>
                <form:input  type="hidden" path="schedule.idSchedule"/>
                <form:input  type="hidden" path="seat"/>
                <form:input  type="hidden" path="firstStation.idStation"/>
                <form:input  type="hidden" path="lastStation.idStation"/>
                <form:input  type="hidden" path="departureTime"/>
                <form:input  type="hidden" path="arrivalTime"/>
                <form:input  type="hidden" path="journeyTime"/>
                <div class="form-group">
                <button type="submit" class="b">OK</button>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>