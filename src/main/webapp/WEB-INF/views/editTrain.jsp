<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../../img/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <title>Edit train</title>
    <style>
        <%@include file="/css/form2.css"%>
    </style>
    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">
    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>
</head>
<body style="background-color: #008ca5">
<div class="nav-item dropdown">
    <form action="/trains" method="get">
        <input type="submit" value="Back" class="backBtn"/>
    </form>
    <a class="link nav-link dropdown-toggle" href="<c:url value="/logout"/>">Logout</a>
</div>
<c:choose>
    <c:when test="${type eq 'createTrain'}"><c:set var="actionUrl" value="createTrain"/></c:when>
    <c:otherwise><c:set var="actionUrl" value="/updateTrain"/></c:otherwise>
</c:choose>
<div class="container" style="  width: 50%">
    <div class="row">
        <div class="col-md-12">
            <form:form action="${actionUrl}" modelAttribute="train"
                       method="POST" name="branch">
                <form:errors path="*" element="div" class="errors"/>
                <form:input type="hidden" path="idTrain"/>
                <div class="form-group">
                    <label for="number">Train number</label>
                    <form:input class="form-control inp" path="number" id="number"/>
                    <form:errors path="number"/>
                </div>
                <div class="form-group">
                    <label for="numberOfSeats">Number of seats</label>
                    <form:input class="form-control inp" path="numberOfSeats" type="number" id="numberOfSeats"/>
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