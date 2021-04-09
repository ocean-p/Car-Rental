<%-- 
    Document   : create
    Created on : Feb 27, 2021, 12:12:42 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Account</title>
        <style type="text/css">
            td{
                padding: 10px;
            }
        </style>
    </head>
    <body>
        <form action="goLogin" method="POST">
            <input type="submit" value="Back to login"/>
        </form>
    <center>
        <h1>Create Account Form</h1>
        <p><font color="grey">Please use gmail for email field. <a href="https://accounts.google.com/signup" target="blank">Click here to create gmail</a>, if you don't have.</font></p>
        <form action="create" method="POST">
            <table>
                <c:set var="err" value="${requestScope.ERRORS}"/>
                <%--email--%>
                <tr>
                    <td><b>Email: </b></td>
                    <td><input type="text" name="txtemail" value="${param.txtemail}"/>(gmail)</td>
                </tr>
                <c:if test="${not empty err.emailErr}">
                    <tr>
                        <td><font color="red">${err.emailErr}</font></td>
                    </tr>
                </c:if>
                <%----%>
                <%--password--%>
                <tr>
                    <td><b>Password: </b></td>
                    <td><input type="password" name="txtpass" value="${param.txtpass}"/></td>
                </tr>   
                <c:if test="${not empty err.passErr}">
                    <tr>
                        <td> <font color="red">${err.passErr}</font></td>
                    </tr>
                </c:if>
                <%----%>
                <%--confirm password--%>
                <tr>
                    <td><b>Confirm Password: </b></td>
                    <td><input type="password" name="txtconfirm" value="${param.txtconfirm}"/></td>
                </tr>   
                <c:if test="${not empty err.cpassErr}">
                    <tr>
                        <td><font color="red">${err.cpassErr}</font></td>
                    </tr>
                </c:if>
                <%----------%>
                <%--phone--%>
                <tr>
                    <td><b>Phone: </b></td>
                    <td><input type="text" name="txtphone" value="${param.txtphone}"/>(10 - 11 numbers)</td>
                </tr>    
                <c:if test="${not empty err.phoneErr}">
                    <tr>
                        <td><font color="red">${err.phoneErr}</font></td>
                    </tr>
                </c:if>
                <%----%>
                <%--name--%>
                <tr>
                    <td><b>Name: </b></td>
                    <td><input type="text" name="txtname" value="${param.txtname}"/>(5 - 50 chars)</td>
                </tr>    
                <c:if test="${not empty err.nameErr}">
                    <tr>
                        <td><font color="red">${err.nameErr}</font></td>
                    </tr>
                </c:if>
                <%----%>    
                <%--address--%>
                <tr>
                    <td><b>Address: </b></td>
                    <td><input type="text" name="txtaddress" value="${param.txtaddress}"/></td>
                </tr>    
                <c:if test="${not empty err.addErr}">
                    <tr>
                        <td><font color="red">${err.addErr}</font></td>
                    </tr>
                </c:if>
                <%----%>           
            </table>
            <br/>
            <input type="submit" value="Create" onclick="return confirm('Are you sure to create?')"/>

        </form>
    </center>
    </body>
</html>
