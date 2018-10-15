<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Detailed information</title>
    <style>
        <%@include file="/css/list.css"%>
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

</head>
<body>
<div class="nav-item dropdown">
    <button class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        Menu
    </button>
    <form action="/logout" method="get">
        <input type="submit" value="Logout"class="login"/>
    </form>
    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        <a class="dropdown-item" href="<c:url value="/"/>">Main page</a>
    </div>
</div>
<div class="main">
    <div class="container">
        <div class="row">
            <p>Branches</p>
            <c:choose>
                <c:when test="${detailedInf.size()=='0'}">
                    <p>No detailed information registered</p>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <tr class="firstTR" style="background-color: #bf4031;">
                            <td>Branch name</td>
                            <td>Stations and its time from previous station</td>
                            <td></td>
                        </tr>
                        <c:forEach items="${branches}" var="branch">
                            <tr>
                                <td>${branch.title}</td>
                                <td>
                                <select class="inp">
                                    <c:forEach var="item" items="${branch.detailedInf}">
                                        <option>${item.station.stationName} ${item.timeFromPrevious.getHours()}: ${item.timeFromPrevious.getMinutes()}</option>
                                    </c:forEach>
                                </select>
                                </td>
                                <td><a href="/createSchedule/${branch.idBranchLine}">
                                    Choose
                                </a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>