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

    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>
    <script src="/js/sockjs.js"></script>
    <script src="/js/stomp.js"></script>
    <script src="/js/rest.js"></script>
    <%--<script src="/js/scheduleSocket.js"></script>--%>
    <script type="text/javascript">
        var stompClient = null;

        function getConnection() {
            var socket = new SockJS('websocket');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                stompClient.subscribe('/topic/schedule', function (greeting) {
                    showGreeting(greeting.body);
                });
            });
        }

        function showGreeting(message) {
            if (message.size != 0) {
                var result = JSON.parse(message);
                $('.change').each(function () {
                    var id = $(this).find('td').attr('id');
                    if (id == result.idSchedule) {
                        $(this).remove().end();
                    }
                });
                $('#table').append('<tr class="change"/>');
                $('#table').find('tr:last').append('<td id="' + result.idSchedule + '">' + result.train + '</td>' +
                    '<td>' + result.firstStation + '</td>' +
                    '<td>' + result.lastStation + '</td>' +
                    '<td>' + result.departureTime + '</td>');
            }
        }

        $(document).ready(function () {
            getConnection();
        });
    </script>
</head>
<body>
<div class="nav-item dropdown">
    <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false">
        Menu
    </button>
    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        <a class="dropdown-item" href="<c:url value="/"/>">Main page</a>
    </div>
    <c:choose>
        <c:when test="${role.title.equals('USER')}">
            <form action="/logout" method="get">
                <input type="submit" value="Logout" class="login"/>
            </form>
        </c:when>
        <c:otherwise>
            <form action="/login" method="get">
                <input type="submit" value="Login" class="login"/>
            </form>
        </c:otherwise>
    </c:choose>
</div>
<div class="main">
    <div class="container">
        <div class="row">
            <p>Schedule</p>
            <table class="table" id="table">
                <tr class="firstTR" style="background-color: #bf4031;">
                    <td id="0">Train number</td>
                    <td id="1">Departure station</td>
                    <td id="2">Arrival station</td>
                    <td id="3">Departure time</td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>