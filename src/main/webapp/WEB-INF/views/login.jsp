<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <title>Login</title>
    <link href="../../img/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <style>
        <%@include file="/css/login.css"%>
    </style>
    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">
    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>
</head>
<body>
<div class="header">
    <form action="/" method="get">
        <input type="submit" value="Main page" class="login" style="margin-top: 10px"/>
    </form>
</div>
<div class="main" style="margin-top: -4%">
    <div class="container">
        <div class="row">
            <div class="col-md-3 login-sec">
                <h2 class="text-center">Login Now</h2>
                <h2 class="text-center">or</h2>
                <h2 class="text-center h2A">
                    <a style="color: #bf4031" href="<c:url value="/registration"/>">Register</a>
                </h2>
                <c:choose>
                    <c:when test="${SPRING_SECURITY_LAST_EXCEPTION!=null}">
                        <p class="errors">Invalid login or password! Please verify.</p>
                    </c:when>
                </c:choose>
                <form:form action="login" method="post">
                    <label class="text-uppercase">Username</label>
                    <input type="e-mail" class="form-control" name="login">
                    <form:errors path = "login"/>
                    <div class="form-group">
                        <label class="text-uppercase">Password</label>
                        <input type="password" class="form-control" name="password">
                    </div>
                    <div class="form-check">
                        <button class="btn btn-login text-center" type="submit">OK</button>
                    </div>
                </form:form>
            </div>
            <div class="col-md-9 banner-sec">
                <img class="imgBanner" src="<c:url value="/img/background.jpg"/>" alt="train"/>
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
