<%-- 
    Document   : active
    Created on : Feb 27, 2021, 11:20:18 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Active Page</title>
    </head>
    <body>
    <center>
        <h1>ACTIVE EMAIL</h1>
        <p><font color="green"><u>${sessionScope.remember.email}</u> has been created successfully !</font></p>
        <p>The code has already sent to this email. Please check and use it to verify.</p>
        <form action="again" method="POST">
            <table>
                <tr>
                    <td><input type="submit" value="Send code again"/></td>
                </tr>
                <c:if test="${not empty requestScope.again}">
                    <tr>
                        <td><font color="blue">${requestScope.again}</font></td>
                    </tr>
                </c:if>
            </table>
        </form>
        <br/>
        <form action="verify" method="POST">
            <table>
                <tr>
                    <td><input type="text" name="usercode" value="${param.usercode}" placeholder="Type code here"/></td>
                    <td><input type="submit" value="Verify"></td> 
                </tr>
                <c:if test="${not empty requestScope.error}">
                    <tr>
                        <td><font color="red">${requestScope.error}</font></td>
                    </tr>
                </c:if>
            </table>
        </form>
    </center>
    </body>
</html>
