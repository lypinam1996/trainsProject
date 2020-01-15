<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <title>Registration</title>
    <style>
        <%@include file="/css/login.css"%>
    </style>
    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">
    <link href="../../img/favicon.ico" rel="shortcut icon" type="image/x-icon" />
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
                <h2 class="text-center">Register Now</h2>
                <h2 class="text-center">or</h2>
                <h2 class="text-center h2A"><a style="color: #bf4031" href="<c:url value="/login"/>">Login</a></h2>
                <form:form action="${login}" modelAttribute="user"
                           class="login-form" method="POST">
                    <form:errors path="*" element="div" class="errors"/>
                    <label class="text-uppercase">Username</label>
                    <form:input path="login" type="e-mail" class="form-control"/>
                    <form:errors path="login"/>
                    <div class="form-group">
                        <label class="text-uppercase">Password</label>
                        <form:input path="password" type="password" class="form-control"/>
                    </div>
                    <div class="form-check">
                        <button type="submit" class="btn btn-login text-center">OK</button>
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
