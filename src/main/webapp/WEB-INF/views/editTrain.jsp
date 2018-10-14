<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit train</title>
    <style>
        <%@include file="/css/form2.css"%>
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body style="background-color: #008ca5">
<div class="nav-item dropdown">
    <form action="/trains" method="get">
        <input type="submit" value="Back" class="backBtn"/>
    </form>
    <form action="/logout" method="get" style="margin-top: -35px">
        <input type="submit" value="Logout" class="login"/>
    </form>
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
                <form:input type="hidden" path="idTrain"/>
                <c:choose>
                    <c:when test="${!error.isEmpty()}">
                        <div class="errors">${error}</div>
                    </c:when>
                </c:choose>
                <div class="form-group">
                    <label  for="number">Train number</label>
                    <form:input class="form-control inp" path="number" id="number"/>
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