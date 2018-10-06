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
                <form:form action="${login}" modelAttribute="user" class="login-form" method="POST">
                        <label for="exampleInputEmail1" class="text-uppercase">Username</label>
                        <form:input path="login" type="text" class="form-control"/>
                    <div class="form-group">
                        <label for="exampleInputPassword1" class="text-uppercase">Password</label>
                        <form:input path="password" type="password" class="form-control"/>
                    </div>
                    <div class="form-check">
                        <button type="submit" class="btn btn-login text-center">Login</button>
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
