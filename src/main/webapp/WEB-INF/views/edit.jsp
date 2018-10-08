<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit detailed information</title>
    <style>
        <%@include file="/css/form.css"%>
        <%@include file="/css/bootstrap.min.css"%>
    </style>
    <style type="text/css">.hidden {display: none;}</style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script type="text/javascript">
        $(function() {

            var index = ${fn:length(employer.detailedInf)};

            $("#add").off("click").on("click", function() {
                $(this).before(function() {
                    var html = '<div id="detailedInf' + index + '.wrapper" class="hidden">';
                    html += '<input type="text" id="detailedInf' + index + '.stationSerialNumber" name="detailedInf[' + index + '].stationSerialNumber" />';
                    html += '<input type="text" id="detailedInf' + index + '.timeFromPrevious" name="detailedInf[' + index + '].timeFromPrevious" />';
                    html += '<select type="text" id="detailedInf' + index + '.station.stationName" name="detailedInf[' + index + '].station.stationName"><c:forEach var="item" items="${stations}"><option value="${item.stationName}"}>${item.stationName}</option></c:forEach> </select>';
                    html += '<button type="button" class="remove" id="'+index+'" ">remove</button>';
                    html += "</div>";
                    return html;
                });
                $("#detailedInf" + index + "\\.wrapper").show();
                index++;
                return false;
            });
        });
        $(document).ready(function () {
            $('html').on('click','.remove', function () {
                console.log(this.id);
                $(this).parent().remove();
            });
        });

    </script>
</head>
<body style="background-color: #008ca5">
<div class="nav-item dropdown">
    <form action="/branches" method="get" >
        <input type="submit" value="Back" class="backBtn"/>
    </form>
    <form action="/logout" method="get" style="margin-top: -35px">
        <input type="submit" value="Logout" class="login"/>
    </form>
</div>
<c:choose>
    <c:when test="${type eq 'create'}"><c:set var="actionUrl" value="create" /></c:when>
    <c:otherwise><c:set var="actionUrl" value="/updateBranch" /></c:otherwise>
</c:choose>
<div class="container">
    <div class="row">
        <form:form action="${actionUrl}" modelAttribute="branch"
                   method="POST" name="branch">
            <table>
                <tr>
                    <td><form:label path="title">Title</form:label></td>
                    <td><form:input path="title" /></td>
                </tr>
                <tr>
                    <td>Detailed information</td>
                    <td>
                        <c:forEach items="${branch.detailedInf}" varStatus="loop" var="di">
                            <div id="detailedInf${loop.index}.wrapper">
                                <form:input path="detailedInf[${loop.index}].stationSerialNumber"  />
                                <form:input path="detailedInf[${loop.index}].timeFromPrevious"  />
                                <form:select path="detailedInf[${loop.index}].station.stationName">
                                    <c:forEach var="item" items="${stations}">
                                        <c:choose>
                                            <c:when test="${di.station.stationName == item.stationName}">
                                                <option selected="selected" value="${di.station.stationName}">${di.station.stationName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option  value="${item.stationName}"}>${item.stationName}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </form:select>
                                <button type="button" class="remove" id="${loop.index}">remove</button>
                            </div>
                        </c:forEach>
                        <button id="add" type="button">add</button>
                    </td>
                </tr>
            </table>
            <button type="submit">OK</button>
        </form:form>
    </div>
</div>

</body>
</html>