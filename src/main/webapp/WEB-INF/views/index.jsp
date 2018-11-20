<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <style>
        <%@include file="/css/mainPage.css"%>
    </style>
    <title>Main page</title>
    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">
    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>

    <script src="/js/websocket.js"></script>
    <script src="/js/sockjs.js"></script>
    <script src="/js/stomp.js"></script>
</head>
<body>
<h1><c:out value="${ pageContext.request.remoteUser}"/> </h1>
<div class="nav-item dropdown">
    <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false">
        Menu
    </button>
    <c:choose>
        <c:when test="${auth.equals('anonymousUser')}">
            <form action="/login" method="get">
                <input type="submit" value="Login" class="login"/>
            </form>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="<c:url value="/schedule"/>">Schedule</a>
                <div class="dropdown-divider"></div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="<c:url value="/schedule"/>">Schedule</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="<c:url value="/tickets"/>">Tickets</a>
            </div>
            <form action="/logout" method="get">
                <input type="submit" value="Logout" class="login"/>
            </form>
        </c:otherwise>
    </c:choose>
</div>
<div class="alert alert-info fade out">
    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    <strong>Info!</strong> You have some tickets for today!
</div>
<div class="container">
    <h1 class="text-center marg">
        <strong>Search for the cheapest train tickets</strong>
    </h1>
    <h2 class="text-center">
        The best way to buy train tickets cheaper
    </h2>
    <form:form action="/" modelAttribute="search" method="POST" class="text-center f">
        <div class="leftAl">
            <label>Departure point</label>
            <label>Arrival point</label>
            <label>Departure time</label>
        </div>
        <form:select path="firstStation" class="inp">
            <c:forEach var="item" items="${stations}">
                <option value="${item.stationName}" }>${item.stationName}</option>
            </c:forEach>
        </form:select>
        <form:select path="lastStation" class="inp">
            <c:forEach var="item" items="${stations}">
                <option value="${item.stationName}" }>${item.stationName}</option>
            </c:forEach>
        </form:select>
        <form:input type="time" class="inp" placeholder="from" path="departureTimeFrom"/>
        <form:errors path="departureTimeFrom"/>
        <form:input type="time" class="inp" placeholder="to" path="departureTimeTo"/><br>
        <button type="submit" class="b">Search</button>
    </form:form>
</div>
</body>
</html>
