<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><!DOCTYPE HTML>
<html>
<head>
    <style type="text/css">.hidden {display: none;}</style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script type="text/javascript">
        $(function() {

            var index = ${fn:length(employer.detailedInf)};

            $("#add").off("click").on("click", function() {
                $(this).before(function() {
                    var html = '<div id="detailedInf' + index + '.wrapper" class="hidden">';
                    html += '<input type="text" id="detailedInf' + index + '.stationSerialNumber" name="detailedInf[' + index + '].stationSerialNumber" />';
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
<body>
<img src="<c:url value="/WEB-INF/img/background.jpg" />" alt="TestDisplay"/>

<c:choose>
    <c:when test="${type eq 'create'}"><c:set var="actionUrl" value="create" /></c:when>
    <c:otherwise><c:set var="actionUrl" value="/updateBranch" /></c:otherwise>
</c:choose>

<form:form action="${actionUrl}" modelAttribute="branch" method="POST" name="branch">
    <table>
        <tr>
            <td><form:label path="title">Title</form:label></td>
            <td><form:input path="title" /></td>
        </tr>
        <tr>
            <td>Detailed information</td>
            <td>
                <c:forEach items="${branch.detailedInf}" varStatus="loop">
                    <div id="detailedInf${loop.index}.wrapper">
                        <form:input path="detailedInf[${loop.index}].stationSerialNumber"  />
                        <button type="button" class="remove" id="${loop.index}">remove</button>
                    </div>
                </c:forEach>
                <button id="add" type="button">add</button>
            </td>
        </tr>
    </table>
    <button type="submit">OK</button>
</form:form>
</body>
</html>