<%-- 
    Document   : home
    Created on : Feb 27, 2021, 11:18:25 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <style type="text/css">
            td{
                padding: 10px;
            }
            form.test{
                display: inline-block;
            }
        </style>
    </head>
    <body>
        <%--not empty user--%>
        <c:if test="${sessionScope.user.isactive == 1}">
            <c:if test="${sessionScope.user.isadmin == 0}">
                <p><font color="blue">Hello, ${sessionScope.user.name}</font></p>
                <form action="logout" method="POST" class="test">
                    <input type="submit" value="Logout" onclick="return confirm('Are you sure to logout?')"/>
                </form>
                <form action="goCart" method="POST" class="test">
                    <input type="submit" value="View Cart"/>
                </form>
                <form action="receive" method="POST" class="test">
                    <input type="submit" value="Receive Sale Code"/>
                </form>
                <form action="history1" method="POST" class="test">
                    <input type="submit" value="History Rental"/>
                </form>
                <c:if test="${not empty requestScope.receive}">
                    <p><font color="green"><b>Sale Code: </b>${receive}</font></p>
                </c:if>
                <c:if test="${not empty requestScope.already}">
                    <p><font color="red">${already}</font></p>
                </c:if>
            </c:if>
        </c:if>
        <%----%>
        <%--empty user--%>    
        <c:if test="${empty sessionScope.user}">
        <form action="goLogin" method="POST">
            <p>Please login before shopping</p>
            <input type="submit" value="Login"/>
        </form>
        </c:if> 
        <%----%>
    <center>
        <%-----------------------%>
        <%--Message from check out--%>
        <c:if test="${not empty success}">
            <p><font color="green">${success}</font></p>
        </c:if>
        <c:if test="${not empty fail}">
            <p><font color="red">${fail}</font></p>
        </c:if>
        <%-----------------------%>
        <h1>RENTAL CAR SHOP 123</h1>
        <form action="home" method="POST">
            <input type="submit" value="Refresh"/>
        </form>
        <p><font color="grey">- Please search for see available cars -</font></p>
        <form action="search" method="POST">
            <table>
                <tr>
                    <td><input type="radio" value="category" name="pick" ${requestScope.radio == 1?"checked":""}/></td>
                    <td><b>Category: </b></td>
                    <td>
                        <select name="cbcategory">
                            <c:forEach var="c" items="${requestScope.category}">
                                <option ${requestScope.number == c.id?"selected":""}>${c.id}-${c.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <c:if test="${not empty message}">
                            <font color="red">${message}</font>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td><input type="radio" value="name" name="pick" ${requestScope.radio == 2?"checked":""}/></td>
                    <td><b>Name: </b></td>
                    <td><input type="text" value="${param.txtname}" name="txtname" placeholder="Name of car"/></td>
                    <td>
                        <c:if test="${not empty namex}">
                            <font color="red">${namex}</font>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><b>Rental Date: </b></td>
                    <td><input type="text" value="${param.txtrental}" name="txtrental" placeholder="dd/mm/yyyy"/></td>
                    <td>
                        <c:if test="${not empty rentx}">
                            <font color="red">${rentx}</font>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><b>Return Date: </b></td>
                    <td><input type="text" value="${param.txtreturn}" name="txtreturn" placeholder="dd/mm/yyyy"/></td>
                    <td>
                        <c:if test="${not empty returnx}">
                            <font color="red">${returnx}</font>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><b>Amount: </b></td>
                    <td><input type="text" value="${param.txtamount}" name="txtamount" placeholder="Amount > 0"/></td>
                    <td>
                        <c:if test="${not empty amountx}">
                            <font color="red">${amountx}</font>
                        </c:if>
                    </td>
                </tr>
            </table>     
            <br/>    
                <input type="submit" value="Search"/>    
        </form>
            <%--Show Result--%>        
            <c:if test="${not empty car}">
                <br/>
                <table width="100%" border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Car Feature</th>
                            <th>Available Quantity</th>
                            <th>Price/1</th>
                            <th>Rating</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="c" items="${car}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>
                                    <ul>
                                        <li><b>ID:</b> ${c.id}</li>
                                        <li><b>Name:</b>  ${c.name}</li>
                                        <li><b>Color:</b>  ${c.color}</li>
                                        <li><b>Year:</b>  ${c.year}</li>
                                        <li><img src="${c.img}" width="120"/></li> 
                                    </ul>
                                </td>
                                <td>${c.quantity}</td>
                                <td>${c.price}</td>
                                <td>${c.rating}</td>
                                <td>
                                    <a href="addToCart?id=${c.id}&rent=${valuerent}&return=${valuereturn}&amount=${valueamount}">Add To Cart</a>
                                </td>
                        <input type="hidden" value="${c.id}" name="carid"/>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>
            </c:if>
            <c:if test="${search == 1}">
                <c:if test="${empty car}">
                    <p>No available cars with the amount during this time.</p>
                </c:if>
            </c:if>
            <%-----------------------%>
            <%--Adding to cart event--%>
            <c:if test="${adding == 1}">
                <c:if test="${not empty success}">
                    <p><font color="green">${success}</font></p>
                </c:if>
                <c:if test="${not empty fail}">
                    <p><font color="red">${fail}</font></p>
                </c:if>
            </c:if>
    </center>         
    </body>
</html>
