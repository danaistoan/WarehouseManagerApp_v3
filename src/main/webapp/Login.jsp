<%--
  Created by IntelliJ IDEA.
  User: dana
  Date: 4/18/2017
  Time: 7:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login Page</title>
    <link href="resources/css/styles.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<h1 style="text-align:center;">Welcome to Warehouse Manager WebApp!</h1>
    <br/>
    <h2 style="text-align:center;">Login</h2>

    <c:if test="${loginFailure}">
        <div class="loginFailure">Invalid username or password</div>
    </c:if>

    <form style="text-align:center;" action="warehouseOperations" method="post">
        <br/>Username:<input type="text" name="username">
        <br/>Password:<input type="password" name="password">
        <input type="hidden" name="action" value="login"/>
        <br/><input type="submit" value="Submit">
    </form>
</body>
</html>
