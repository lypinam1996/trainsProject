<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Login</title>
    <style>
        <%@include file="/css/login.css"%>
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div class="main">
    <div class="container">
        <div class="row">
            <div class="col-md-3 login-sec">
                <h2 class="text-center">Login Now</h2>
                <h2 class="text-center">or</h2>
                <h2 class="text-center h2A"><a style="color: #bf4031" href="<c:url value="/registration"/>">Register</a></h2>
                <c:url value="/j_spring_security_check" var="loginUrl" />
                <form:form action="${loginUrl}" method="post">
                    <label  class="text-uppercase">Username</label>
                    <input type="text" class="form-control" name="login">
                <div class="form-group">
                    <label  class="text-uppercase">Password</label>
                    <input type="password" class="form-control" name="password">
                </div>
                <div class="form-check">
                    <button class="btn btn-login text-center" type="submit">Войти</button>
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
