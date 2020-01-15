<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../../img/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <title>Edit detailed information</title>
    <style>
        <%@include file="/css/form.css"%>
        <%@include file="/css/bootstrap.min.css"%>
    </style>
    <style type="text/css">.hidden {
        display: none;
    }</style>
    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>
    <script type="text/javascript">
        $(function () {

            var index = ${fn:length(branch.detailedInf)};

            $("#add").off("click").on("click", function () {
                $(this).before(function () {
                    var html = '<div id="detailedInf' + index + '.wrapper" class="hidden">';
                    html += '<input  class="inp" type="number" id="detailedInf' + index + '.stationSerialNumber" name="detailedInf[' + index + '].stationSerialNumber" />';
                    html += '<input class="inp a" type="time" value="00:00" id="detailedInf' + index + '.timeFromPrevious" name="detailedInf[' + index + '].timeFromPrevious" />';
                    html += '<select class="inp" type="text" id="detailedInf' + index + '.station.stationName" name="detailedInf[' + index + '].station.stationName"><c:forEach var="item" items="${stations}"><option value="${item.stationName}"}>${item.stationName}</option></c:forEach> </select>';
                    html += '<button type="button" class="remove b" id="' + index + '" ">remove</button>';
                    html += "</div>";
                    return html;
                });
                $("#detailedInf" + index + "\\.wrapper").show();
                index++;
                console.log(index);
                return false;
            });
        });

        $(document).ready(function () {
            $('html').on('click', '.remove', function () {
                $(this).parent().remove();
            });
        });


    </script>
</head>
<body style="background-color: #008ca5">
<div class="nav-item dropdown">
    <form action="/branches" method="get">
        <input type="submit" value="Back" class="backBtn"/>
    </form>
    <form action="/logout" method="get" style="margin-top: -35px">
        <input type="submit" value="Logout" class="login"/>
    </form>
</div>
<c:choose>
    <c:when test="${type eq 'createBranch'}"><c:set var="actionUrl" value="createBranch"/></c:when>
    <c:otherwise><c:set var="actionUrl" value="/updateBranch"/></c:otherwise>
</c:choose>
<div class="container">
    <div class="row">
        <form:form action="${actionUrl}" modelAttribute="branch"
                   method="POST" name="branch">
            <table>
                <tr>
                    <td><form:label path="title"><p style="font-size: 15pt">Branch title</p></form:label></td>
                    <td><form:input path="title" class="inp"/></td>
                    <form:input path="idBranchLine" type="hidden"/>
                </tr>
                <tr>
                    <td></td>
                    <td style="font-size: 14pt">
                        <c:choose>
                            <c:when test="${!errors.isEmpty()}">
                                <div class="errors">
                                    <c:forEach items="${errors}" var="error">
                                        <div>${error}</div>
                                    </c:forEach>
                                </div>
                            </c:when>
                        </c:choose>
                        <div class="row">
                            <div style="width: 23%; margin-left: 15px">Station serial number</div>
                            <div style="width: 23%">Time from previous station</div>
                            <div>Next station</div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><p style="font-size: 15pt">Detailed information</p></td>
                    <td>
                        <c:forEach items="${branch.detailedInf}" varStatus="loop" var="di">
                            <div id="detailedInf${loop.index}.wrapper">
                                <form:input type="hidden" path="detailedInf[${loop.index}].idDetailedInfBranch"/>
                                <form:input type="number" class="inp"
                                            path="detailedInf[${loop.index}].stationSerialNumber"/>
                                <form:input class="inp" type="time" path="detailedInf[${loop.index}].timeFromPrevious"
                                            value="${di.toTime()}"/>
                                <form:select path="detailedInf[${loop.index}].station.stationName" class="inp">
                                    <c:forEach var="item" items="${stations}">
                                        <c:choose>
                                            <c:when test="${di.station.stationName == item.stationName}">
                                                <option class="inp" selected="selected"
                                                        value="${di.station.stationName}">${di.station.stationName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option class="inp" value="${item.stationName}"
                                                        }>${item.stationName}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </form:select>
                                <button type="button" class="remove b" id="${loop.index}">remove</button>
                            </div>
                        </c:forEach>
                        <button id="add" type="button" class="b">add</button>
                    </td>
                </tr>
            </table>
            <button type="submit" class="login">OK</button>
        </form:form>
    </div>
</div>
</body>
</html>