<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <%--<%@ page isELIgnored="false" %>--%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  ${user.get(0).login}
</body>
</html>