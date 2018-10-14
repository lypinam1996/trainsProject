<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Branches</title>
    <style>
        <%@include file="/css/list.css"%>
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

</head>
<body>
<div class="nav-item dropdown">
    <form action="/logout" method="get">
        <input type="submit" value="Logout" class="login" role="button" style="margin-top: 1%"/>
    </form>
</div>
<div class="main">
    <div class="container">
        <div class="row">
            <p>Branches</p>
            <c:choose>
                <c:when test="${branches.size()=='0'}">
                    <p>No branches registered</p>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <tr class="firstTR" style="background-color: #bf4031;">
                            <td>Branch title</td>
                            <td></td>
                            <td></td>
                        </tr>
                        <c:forEach items="${branches}" var="branch">
                            <tr>
                                <td>${branch.title}</td>
                                <td><a href="/detailedInf/${branch.idBranchLine}">
                                    Detailed information
                                </a>
                                </td>
                                <td><a href="/updateBranch/${branch.idBranchLine}">
                                    Edit
                                </a></td>
                            </tr>
                        </c:forEach>
                        <tr style="background-color: #CBEEF4"><td><form action="/createBranch" method="get">
                            <input type="submit" value="Add new branch" style="margin-top: 1%; margin-left: 1%" class="login"/>
                        </form></td><td></td><td></td></tr>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>