<%-- 
    Document   : viewcart
    Created on : Mar 4, 2021, 7:46:31 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
        <style type="text/css">
            td{
                text-align: center;
            }
            td.feature{
                text-align: left;
            }
            td.total{
                text-align: right;
            }
            span{
                padding: 20px;
                font-size: 20px;
            }
            a.minus,a.plus{
                text-decoration: none;
                padding: 10px;
                color: white;
            }
            a.minus{
                background-color: red;
            }
            a.plus{
                background-color: blue;
            }
            td.payment,td.code,td.add{
                padding: 10px;
            }
            td.add{
                font-size: 20px;;
            }
     
        </style>
    </head>
    <body>
        <form action="home" method="POST">
            <input type="submit" value="Back to home"/>
        </form>
    <center>
        <h1>YOUR CART</h1>
        <c:if test="${not empty requestScope.warning}">
            <p><font color="red">${warning}</font></p>
        </c:if>
        <c:if test="${not empty requestScope.message}">
            <c:forEach var="m" items="${message}">
                <p><font color="red">${m}</font></p>
            </c:forEach>
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
        <c:if test="${not empty sessionScope.cart}">
            <table width="100%" border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Car Feature</th>
                        <th>Rent Date</th>
                        <th>Return Date</th>
                        <th>Price/1</th>
                        <th>Amount</th>
                        <th>Price/Amount</th>
                        <th>Option</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="total" value="0" scope="session"></c:set>
                    <c:forEach var="c" items="${sessionScope.cart}" varStatus="d">
                        <c:set var="total" value="${total+c.car.price*c.amount}" scope="session"></c:set>
                            <tr>
                                <td>${d.count}</td>
                            <td class="feature">
                                <ul>
                                    <li><b>ID: </b> ${c.car.id}</li>
                                    <li><b>Name: </b> ${c.car.name}</li>
                                    <li><b>Color: </b> ${c.car.color}</li>
                                    <li><b>Year: </b> ${c.car.year}</li>
                                    <li><img src="${c.car.img}" width="100"/></li>
                                </ul>
                            </td>
                            <td>${c.rentdate}</td>
                            <td>${c.returndate}</td>
                            <td>${c.car.price}</td>
                            <td><span><a href="minus?id=${c.car.id}&rent=${c.rentdate}&return=${c.returndate}" class="minus">-</a></span> ${c.amount} <span><a href="plus?id=${c.car.id}&rent=${c.rentdate}&return=${c.returndate}" class="plus">+</a></span></td>
                            <td>${c.car.price*c.amount}</td>
                            <td><a href="remove?id=${c.car.id}&rent=${c.rentdate}&return=${c.returndate}" onclick="return confirm('Are you sure to remove?')">Remove</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tr>
                    <td colspan="6" class="total"><b>Total: </b></td>
                    <td>${total}$</td>
                    <td></td>
                </tr>
                <c:if test="${not empty sessionScope.sale}">
                    <tr>
                        <td colspan="6" class="total"><b>Code Sale: </b>${sale.code}(${sale.percent}%)</td>
                        <td>${total - total*sale.percent/100}$</td>
                        <td><a href="removeCode" onclick="return confirm('Are you sure to remove this code?')">Remove Code</a></td>
                    </tr>
                </c:if>
            </table>
            <br/>
            <c:if test="${empty sessionScope.sale}">
                <form action="addCode" method="POST">
                    <table>
                        <tr>
                            <td><b>Code Sale:</b></td>
                            <td class="code"><input type="text" value="${param.txtcode}" name="txtcode" placeholder="Type here and add or skip"/></td>
                            <td class="add"><input type="submit" value="Add"/></td>
                        </tr>
                        <c:if test="${not empty requestScope.err}">
                            <tr>
                                <td><font color="red">${requestScope.err}</font></td>
                            </tr>
                        </c:if>
                    </table>
                </form>    
            </c:if>
            <form action="rent" method="POST">
                <table>
                    <tr>
                        <td class="payment">*<b>Payment: </b></td>
                        <td class="payment">
                            <select name="payment">
                                <option>Cash</option>
                                <option>Bank</option>
                            </select>
                        </td> 
                    </tr>
                    
                </table>
                <br/>
                <input type="submit" value="Check Out" onclick="return confirm('Click OK to finish')"/>
            </form>
        </c:if>
        <c:if test="${empty sessionScope.cart}">
            <p><font color="grey">No car in cart</font></p>
        </c:if>
    </center>
    </body>
</html>
