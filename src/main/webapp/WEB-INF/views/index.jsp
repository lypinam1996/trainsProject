<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <link href="../../img/favicon.ico" rel="shortcut icon" type="image/x-icon"/>
    <style>
        <%@include file="/css/mainPage.css"%>
    </style>
    <title>Main page</title>
    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/sunny/jquery-ui.css">
    <script src="/js/jquery.min.js"></script>
    <script src="/js/jquery-ui.min.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>

    <script src="/js/websocket.js"></script>
    <script src="/js/sockjs.js"></script>
    <script src="/js/stomp.js"></script>
    <script src="/js/stations.js"></script>
    <style>
        .link:hover {
            color: white;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="nav-item dropdown">
    <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false">
        Menu
    </button>
    <c:choose>
        <c:when test="${role.title.equals('USER')}">
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="<c:url value="/schedule"/>">Schedule</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="<c:url value="/tickets"/>">Tickets</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="<c:url value="/trackIndicator"/>">Track indicator</a>
            </div>
            <a class="link nav-link dropdown-toggle" href="<c:url value="/logout"/>">Logout</a>
        </c:when>
        <c:otherwise>
            </button>
            <a class="link nav-link dropdown-toggle" href="<c:url value="/logout"/>">Login</a>
            <a class="link nav-link dropdown-toggle" href="<c:url value="/google"/>">Login with google</a>
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
        <form:input type="text" class="inp" path="firstStation" placeholder="from"/>
        <form:input type="text" class="inp" path="lastStation" placeholder="to"/>
        <form:input type="time" class="inp" placeholder="from" path="departureTimeFrom"/>
        <form:errors path="departureTimeFrom"/>
        <form:input type="time" class="inp" placeholder="to" path="departureTimeTo"/><br>
        <button type="submit" class="b">Search</button>
    </form:form>
</div>
</body>
</html>
