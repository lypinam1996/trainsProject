<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <title>Main page</title>
    <style>
        <%@include file="/css/login.css"%>
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div class="header">
    <form action="/logout" method="get">
        <input type="submit" value="Logout" class="login" style="margin-top: 10px"/>
    </form>
</div>
<div class="main" style="margin-top: -4%">
    <div class="container">
        <div class="row">
            <div class="col-md-3" style="display: table">
                <p style="font-size: 25pt; margin-top: 50%">Go to...</p>
                <p><a class="aStyle" href="<c:url value="/stations"/>">Stations</a></p>
                <p><a class="aStyle" href="<c:url value="/branches"/>">Branches</a></p>
                <p><a class="aStyle" href="<c:url value="/trains"/>">Trains</a></p>
                <p><a class="aStyle" href="<c:url value="/schedule"/>">Schedule</a></p>
            </div>

            <div class="col-md-9 banner-sec">
                <img class="imgBanner" src="<c:url value="/img/worker.jpg"/>" alt="train"/>
                <div class="d-md-block">
                    <div class="banner-text">
                        <h2>Search for the cheapest train tickets</h2>
                        <p>The best way to buy train tickets cheaper</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
