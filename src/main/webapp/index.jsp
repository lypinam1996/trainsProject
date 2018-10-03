<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Hello</title>
    <style>
        <%@include file="/WEB-INF/css/mainPage.css"%>
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body class="bodystyle">
<div class="nav-item dropdown">
    <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        Menu
    </button>
    <input type="button" class="login" value="Login"/>
    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        <a class="dropdown-item" href="#">Action</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="#">Something else here</a>
    </div>
    <%--<img src="http://wallsdesk.com/wp-content/uploads/2016/10/Sri-Lanka-Wallpapers-HD.jpg" />--%>
</div>
<div class="container">
    <h1 class="text-center marg"><strong>Search for the cheapest train tickets</strong></h1>
    <h2 class="text-center">The best way to buy train tickets cheaper</h2>
    <form class="text-center f">
        <label>Departure point</label>
        <label>Point of arrival</label>
        <label>Departure time</label>
        <label>Time of arrival</label><br>
    <input type="text" class="inp"/>
    <input type="text" class="inp"/>
    <input type="text" class="inp"/>
    <input type="text" class="inp"/><br>
    <button type="submit" class="b">Search</button>
    </form>
</div>

</body>
</html>
