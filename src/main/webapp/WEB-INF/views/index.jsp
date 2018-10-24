<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <title>Main page</title>
    <style>
        <%@include file="/css/mainPage.css"%>
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body class="bodystyle">
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
                <div class="dropdown-divider">  </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="<c:url value="/schedule"/>">Schedule</a>
                <div class="dropdown-divider">  </div>
                <a class="dropdown-item" href="<c:url value="/tickets"/>">Tickets</a>
            </div>
            <form action="/logout" method="get">
                <input type="submit" value="Logout" class="login"/>
            </form>
        </c:otherwise>
    </c:choose>
</div>
<div class="container">
    <h1 class="text-center marg"><strong>Search for the cheapest train tickets</strong></h1>
    <h2 class="text-center">The best way to buy train tickets cheaper</h2>
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
