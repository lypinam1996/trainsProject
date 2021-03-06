<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../../img/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <title>Edit station</title>
    <style>
        <%@include file="/css/form2.css"%>
    </style>
    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">
    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>
</head>
<body style="background-color: #008ca5">
<div class="nav-item dropdown">
    <form action="/stations" method="get">
        <input type="submit" value="Back" class="backBtn"/>
    </form>
    <a class="link nav-link dropdown-toggle" href="<c:url value="/logout"/>">Logout</a>
</div>
<c:choose>
    <c:when test="${type eq 'createStation'}"><c:set var="actionUrl" value="createStation"/></c:when>
    <c:otherwise><c:set var="actionUrl" value="/updateStation"/></c:otherwise>
</c:choose>
<div class="container" style="  width: 50%">
    <div class="row">
        <div class="col-md-12">
            <form:form action="${actionUrl}" modelAttribute="station"
                       method="POST">
                <form:errors path = "*"  element = "div" class="errors"/>
                <form:input type="hidden" path="idStation"/>
                <div class="form-group">
                    <label for="stationName">Station name</label>
                    <form:input class="form-control inp" path="stationName"/>
                    <form:errors path = "stationName"/>
                </div>
                <div class="form-group">
                    <button type="submit" class="b">OK</button>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>