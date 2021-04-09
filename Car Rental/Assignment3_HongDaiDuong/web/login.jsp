<%-- 
    Document   : login
    Created on : Feb 25, 2021, 1:21:17 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <script src="https://www.google.com/recaptcha/api.js?hl=en" async defer></script>
    </head>
    <body>
        <form action="home" method="POST">
            <input type="submit" value="Back to home"/>
        </form>
    <center>
        <h1>LOGIN</h1>
        <c:if test="${not empty error}">
            <p><font color="red"><b>${error}</b></font></p>
        </c:if>
        <form action="login" method="POST">
            <table>
                <tr>
                    <td><b>Email: </b></td>
                    <td><input type="text" name="email" value="${param.email}" /></td>
                </tr>

                <tr>
                    <td><b>Password: </b></td>
                    <td><input type="password" name="password" value=""/></td>
                </tr>
            </table>
            <br/>
            <div class="g-recaptcha" data-sitekey="6LdF9WYaAAAAAA_Jm52fhk-ZXo8CdBPxf6wki0QO"></div>
            <br/>
            <input type="submit" value="Login"/>
        </form>
        <br/>    
        <form action="createForm" method="POST">
            <input type="submit" value="Create Account"/>
        </form>    
    </center>
    </body>
</html>
