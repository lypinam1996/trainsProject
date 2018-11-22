<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit schedule</title>
    <style>
        <%@include file="/css/form2.css"%>
    </style>
    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">
    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>
</head>
<body style="background-color: #008ca5">
<div class="nav-item dropdown">
    <form action="/schedule" method="get">
        <input type="submit" value="Back" class="backBtn"/>
    </form>
    <form action="/logout" method="get" style="margin-top: -35px">
        <input type="submit" value="Logout" class="login"/>
    </form>
</div>
<c:choose>
    <c:when test="${type eq 'updateSchedule'}"><c:set var="actionUrl" value="/updateSchedule"/></c:when>
    <c:otherwise><c:set var="actionUrl" value="/createSchedule"/></c:otherwise>
</c:choose>
<div class="container" style="  width: 50%">
    <div class="row">
        <div class="col-md-12">
            <form:form action="${actionUrl}" modelAttribute="schedule"
                       method="POST" name="branch">
            <form:input type="hidden" path="idSchedule"/>
            <form:input type="hidden" path="branch.idBranchLine"/>
            <c:choose>
                <c:when test="${!errors.isEmpty()}">
                    <div class="errors">
                        <c:forEach items="${errors}" var="error">
                            <div>${error}</div>
                        </c:forEach>
                    </div>
                </c:when>
            </c:choose>
            <div class="form-group">
                <label for="train">Train</label>
                <form:select path="train.idTrain" class="form-control inp">
                    <c:forEach var="item" items="${trains}">
                        <c:choose>
                            <c:when test="${schedule.train.idTrain == item.idTrain}">
                                <option class="inp" selected="selected"
                                        value="${schedule.train.idTrain}">${schedule.train.number}</option>
                            </c:when>
                            <c:otherwise>
                                <option class="inp" value="${item.idTrain}" }>${item.number}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </div>
            <div class="form-group">
                <label for="firstStation">First station</label>
                <form:select path="firstStation.idStation" class="form-control inp">
                    <c:forEach var="item" items="${stations}">
                        <c:choose>
                            <c:when test="${schedule.firstStation.idStation == item.idStation}">
                                <option class="inp" selected="selected"
                                        value="${schedule.firstStation.idStation}">${schedule.firstStation.stationName}</option>
                            </c:when>
                            <c:otherwise>
                                <option class="inp" value="${item.idStation}">${item.stationName}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </div>
                <div class="form-group">
                    <label for="lastStation">First station</label>
                    <form:select path="lastStation.idStation" class="form-control inp">
                        <c:forEach var="item" items="${stations}">
                            <c:choose>
                                <c:when test="${schedule.lastStation.idStation == item.idStation}">
                                    <option class="inp" selected="selected"
                                            value="${schedule.lastStation.idStation}">${schedule.lastStation.stationName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option class="inp" value="${item.idStation}">${item.stationName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="form-group">
                    <label for="departureTime">Departure time</label>
                    <form:input type="time" class="form-control inp" path="departureTime"/>
                </div>
                <div class="form-group">
                    <button type="submit" class="b">OK</button>
                </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>