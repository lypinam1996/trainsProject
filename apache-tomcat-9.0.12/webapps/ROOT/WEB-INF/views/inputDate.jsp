<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../../img/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <title>Input date</title>
    <style>
        <%@include file="/css/form2.css"%>
    </style>
    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">
    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>
</head>
<body style="background-color: #008ca5">
<div class="nav-item dropdown">
    <form action="/home" method="get">
        <input type="submit" value="Main page" class="backBtn"/>
    </form>
    <form action="/logout" method="get" style="margin-top: -35px">
        <input type="submit" value="Logout" class="login"/>
    </form>
</div>
<div class="container" style="  width: 50%">
    <div class="row">
        <div class="col-md-12">
            <form:form action="/chooseTicket" modelAttribute="ticket"
                       method="POST">
                <form:errors path="*" element="div" class="errors"/>
                <c:choose>
                    <c:when test="${!error.isEmpty()}">
                        <div class="errors">${error}</div>
                    </c:when>
                </c:choose>
                <div class="form-group">
                    <label for="departureDate">Departure date</label>
                    <form:input class="form-control inp" type="date" path="departureDate" required="required"/>
                    <form:errors path = "departureDate"/>
                </div>
                <div class="form-group">
                    <label for="passanger.name">Name</label>
                    <form:input class="form-control inp" path="passanger.name" required="required"/>
                </div>
                <div class="form-group">
                    <label for="passanger.surname">Surname</label>
                    <form:input class="form-control inp" path="passanger.surname" required="required"/>
                </div>
                <div class="form-group">
                    <label for="passanger.patronymic">Patronymic</label>
                    <form:input class="form-control inp" path="passanger.patronymic"/>
                </div>
                <div class="form-group">
                    <label for="passanger.dateOfBirth">Date of birth</label>
                    <form:input class="form-control inp" type="date" path="passanger.dateOfBirth" required="required"/>
                </div>
                <form:input type="hidden" path="idTicket"/>
                <form:input type="hidden" path="schedule.idSchedule"/>
                <form:input type="hidden" path="seat"/>
                <form:input type="hidden" path="firstStation.idStation"/>
                <form:input type="hidden" path="lastStation.idStation"/>
                <form:input type="hidden" path="departureTime"/>
                <form:input type="hidden" path="arrivalTime"/>
                <form:input type="hidden" path="journeyTime"/>
                <div class="form-group">
                    <button type="submit" class="b">OK</button>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>