<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Track indicator</title>
    <style>
        <%@include file="/css/list.css"%>
    </style>
    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/sunny/jquery-ui.css">
    <link href="../../img/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>
    <script src="/js/sockjs.js"></script>
    <script src="/js/stomp.js"></script>
    <script src="/js/schedule.js"></script>
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
    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        <a class="dropdown-item" href="<c:url value="/schedule"/>">Schedule</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/tickets"/>">Tickets</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/home"/>">Main page</a>
    </div>
    <a class="link nav-link dropdown-toggle" href="<c:url value="/logout"/>">Logout</a>
</div>
<div class="main">
    <div class="container">
        <div class="row">
            <p>Track indicator</p>
            <table class="table" id="table">
                <tr class="firstTR" style="background-color: #bf4031;">
                    <td>Train number</td>
                    <td>Departure station</td>
                    <td>Arrival station</td>
                    <td>Departure time</td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>