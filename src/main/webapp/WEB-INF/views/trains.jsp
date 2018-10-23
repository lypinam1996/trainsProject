<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trains</title>
    <style>
        <%@include file="/css/list.css"%>
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div class="nav-item dropdown">
    <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false">
        Menu
    </button>
    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        <a class="dropdown-item" href="<c:url value="/"/>">Main page</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/branches"/>">Branches</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/schedule"/>">Schedule</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="<c:url value="/stations"/>">Stations</a>
        <div class="dropdown-divider"></div>
    </div>
    <form action="/logout" method="get">
        <input type="submit" value="Logout" class="login" role="button"/>
    </form>
</div>
<div class="main">
    <div class="container">
        <div class="row">
            <c:choose>
                <c:when test="${errors.size()!=0}">
                    <div class="errors">
                        <c:forEach items="${errors}" var="error">
                            <div>${error}</div>
                        </c:forEach>
                    </div>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${trains.size()=='0'}">
                    <p>No trains registered</p>
                </c:when>
                <c:otherwise>
                    <p>Trains</p>
                    <table class="table">
                        <tr class="firstTR" style="background-color: #bf4031;">
                            <td>Train number</td>
                            <td>Number of seats</td>
                            <td></td>
                            <td></td>
                        </tr>
                        <c:forEach items="${trains}" var="train">
                            <tr>
                                <td>${train.number}</td>
                                <td>${train.numberOfSeats}</td>
                                <td><a href="/updateTrain/${train.idTrain}">
                                    Edit
                                </a></td>
                                <td><a href="/deleteTrain/${train.idTrain}">
                                    Delete
                                </a></td>
                            </tr>
                        </c:forEach>
                        <tr style="background-color: #CBEEF4"><td><form action="/createTrain" method="get">
                            <input type="submit" value="Add new train" style="margin-top: 1%; margin-left: 1%" class="login"/>
                        </form></td><td></td><td></td></tr>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>