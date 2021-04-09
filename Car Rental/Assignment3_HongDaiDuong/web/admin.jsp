<%-- 
    Document   : admin
    Created on : Feb 27, 2021, 11:19:38 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body>
        <c:if test="${sessionScope.user.isactive == 1}">
            <c:if test="${sessionScope.user.isadmin == 1}">
                <p><font color="blue">Hello, ${sessionScope.user.name}</font></p>
                <form action="logout" method="POST">
                    <input type="submit" value="Logout" onclick="return confirm('Are you sure to logout?')"/>
                </form>
            <center>
                <h2>Admin side hasn't been developing yet.</h2>
            </center>
            </c:if>
        </c:if>
    </body>
</html>
