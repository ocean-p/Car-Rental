<%-- 
    Document   : history2
    Created on : Mar 7, 2021, 1:06:15 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
        <style type="text/css">
            form.test{
                display: inline-block;
            }
            td{
                text-align: center;
            }
            td.rent{
                text-align: left;
            }
            td.detail{
                background: blue;
            }
            a.detail{
                text-decoration: none;
                color: white;
            }
        </style>
    </head>
    <body>
        <form action="home" method="POST" class="test">
            <input type="submit" value="Back to home"/>
        </form>
        <form action="history2" method="POST" class="test">
            <input type="submit" value="Refresh"/>
        </form>
        <form action="history1" method="POST" class="test">
            <input type="submit" value="Current Rental"/>
        </form>
    <center>
        <h1><font color="red">Cancel Rental</font></h1>
        <c:if test="${not empty requestScope.history}">
            <table width="100%" border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Rental</th>
                        <th>Total Quantity</th>
                        <th>Total Price</th>
                        <th>Payment</th>
                        <th>Sale Code</th>
                        <th>Option</th>
                    </tr>
                </thead>
                <tbody>
                    
                    <c:forEach var="h" items="${history}" varStatus="d">
                        
                        <tr>
                            <td>${d.count}</td>
                        <td class="rent">
                            <ul>
                                <li><b>ID: </b>${h.id}</li>
                                <li><b>Create Date: </b>${h.createdate}</li>
                            </ul>
                        </td>
                        <td>${h.totalquantity}</td>
                        <td>${h.totalprice}</td>
                        <td>${h.payment}</td>
                        <td>${h.code}</td>
                        
                        <td class="detail"><a href="detail2?id=${h.id}" class="detail">Detail</a></td>        
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty requestScope.history}">
            <c:if test="${empty requestScope.detail}">
                <h2><font color="grey">No Cancel Rental. </font></h2>
            </c:if>
        </c:if>
        <c:if test="${not empty requestScope.detail}">
            <h2><font color="blue">Detail - Rental id: ${id}</font></h2>
            <table width="100%" border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Car Feature</th>
                        <th>Quantity</th>
                        <th>Total Price</th>
                        <th>Rental Date</th>
                        <th>Return Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="d" items="${detail}" varStatus="c">
                        <tr>
                             <td>${c.count}</td>
                             <td class="rent">
                                 <ul>
                                     <li><b>ID: </b>${d.carid}</li>
                                     <li><b>Name: </b>${d.carname}</li>
                                     <li><b>Unit Price: </b>${d.unitprice}</li>
                                 </ul>
                             </td>
                             <td>${d.quantity}</td>
                             <td>${d.totalprice}</td>
                             <td>${d.rentdate}</td>
                             <td>${d.returndate}</td>
                             
                        </tr>
                    </c:forEach>
                </tbody>    
            </table>
        </c:if>        
    </center>
    </body>
</html>
