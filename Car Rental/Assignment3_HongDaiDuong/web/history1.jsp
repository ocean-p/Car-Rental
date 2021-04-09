<%-- 
    Document   : history1
    Created on : Mar 7, 2021, 8:11:37 AM
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
            td{
                text-align: center;
            }
            td.rent{
                text-align: left;
            }
            td.cancel{
                background-color: red;
            }
            td.detail{
                background-color: blue;
            }
            a.cancel,a.detail{
                text-decoration: none;
                color: white;
            }
            form.test{
                display: inline-block;
            }
            td.rating{
                width: 50px;
            }
        </style>
    </head>
    <body>
        <form action="home" method="POST" class="test">
            <input type="submit" value="Back to home"/>
        </form>
        <form action="history1" method="POST" class="test">
            <input type="submit" value="Refresh"/>
        </form>
        <form action="history2" method="POST" class="test">
            <input type="submit" value="Cancel Rental"/>
        </form>
    <center>
        <c:if test="${not empty requestScope.notcancel}">
            <p><font color="red">${notcancel}</font></p>
        </c:if>
        <c:if test="${not empty requestScope.cancancel}">
            <p><font color="green">${cancancel}</font></p>
        </c:if>    
        <h1>Rental History</h1>
        <form action="searchDate" method="POST">
            <table>
                <tr>
                    <td><input type="text" name="txtdate" value="${param.txtdate}" placeholder="dd/mm/yy"/></td>
                    <td><input type="submit" value="Search By Date"/></td>
                </tr>
                <c:if test="${not empty errdate}">
                    <tr>
                        <td><font color="red">${errdate}</font></td>
                    </tr>
                </c:if>
            </table>
        </form>
        <br/>            
        <form action="searchName" method="POST">
            <table>
                <tr>
                    <td><input type="text" name="txtname" value="${param.txtname}" placeholder="name of car"/></td>
                    <td><input type="submit" value="Search By Name"/></td>
                </tr>
                <c:if test="${not empty errname}">
                    <tr>
                        <td><font color="red">${errname}</font></td>
                    </tr>
                </c:if>
            </table>
        </form>
                    <c:if test="${requestScope.searchevent == 1}">
                        <h2><font color="violet">- Search Result -</font></h2>
                    </c:if>            
        <br/>            
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
                        <th colspan="2">Option</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="used" value="0" scope="session"></c:set>
                    <c:forEach var="h" items="${history}" varStatus="d">
                        <c:set var="used" value="${used+h.totalprice}" scope="session"></c:set>
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
                        
                        <td class="cancel">
                            
                            <a href="Cancel?id=${h.id}" class="cancel" onclick="return confirm('Are you sure to cancel?')">Cancel</a>
                            
                        </td>        
                        <td class="detail"><a href="detail?id=${h.id}" class="detail">Detail</a></td>        
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
                    <h2><font color="blue">Total Used: ${used}</font></h2>      
        </c:if>
        <c:if test="${empty requestScope.history}">
            <c:if test="${empty requestScope.detail}">
                <c:if test="${empty requestScope.form}">
                    <h3><font color="grey">No current rental available.</font></h3>
                </c:if>
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
                        <th colspan="2">FeedBack - Rating</th>
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
                            <td>
                               
                                <a href="form?rentid=${id}&carid=${d.carid}&rentdate=${d.rentdate}&returndate=${d.returndate}">Feed Back</a>
                                            
                            </td>
                            <td class="rating">${d.rating}</td>
                        </tr>
                    </c:forEach>
                </tbody>    
            </table>
        </c:if>
        <c:if test="${requestScope.form == 1}">
        <h2><font color="green">Doing feedback</font></h2>
        <!-- already feedback   -->
        <c:if test="${requestScope.already == 1}">
            <h3><font color="green">You have already got feedback this car.</font></h3>
            <table width="100%" border="1">
                <thead>
                    <tr>
                        <th>Car Feature</th>
                        <th>Quantity</th>
                        <th>Total Price</th>
                        <th>Rental Date</th>
                        <th>Return Date</th>
                        <th>Feedback Rating</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="rent">
                            <ul>
                                <li><b>ID: </b>${rd.carid}</li>
                                <li><b>Name: </b>${rd.carname}</li>
                                <li><b>Unit Price: </b>${rd.unitprice}</li>
                            </ul>
                        </td>
                        <td>${rd.quantity}</td>
                        <td>${rd.totalprice}</td>
                        <td>${rd.rentdate}</td>
                        <td>${rd.returndate}</td>
                        <td>${rd.rating}</td>
                    </tr>
                </tbody>
            </table>
        </c:if>
        <!-- not time feedback   -->
        <c:if test="${requestScope.notfeedback == 1}">
            <h3><font color="blue">This time you can't got feedback, please come back later. </font></h3>
            <table width="100%" border="1">
                <thead>
                    <tr>
                        <th>Car Feature</th>
                        <th>Quantity</th>
                        <th>Total Price</th>
                        <th>Rental Date</th>
                        <th>Return Date</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="rent">
                            <ul>
                                <li><b>ID: </b>${rd.carid}</li>
                                <li><b>Name: </b>${rd.carname}</li>
                                <li><b>Unit Price: </b>${rd.unitprice}</li>
                            </ul>
                        </td>
                        <td>${rd.quantity}</td>
                        <td>${rd.totalprice}</td>
                        <td>${rd.rentdate}</td>
                        <td>${rd.returndate}</td>
                    </tr>
                </tbody>
            </table>
        </c:if>
        <!-- can feedback   -->
        <c:if test="${canfeedback == 1}">
        <form action="send" method="POST">
            <table width="100%" border="1">
                <thead>
                    <tr>
                        <th>Car Feature</th>
                        <th>Quantity</th>
                        <th>Total Price</th>
                        <th>Rental Date</th>
                        <th>Return Date</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="rent">
                            <ul>
                                <li><b>ID: </b>${rd.carid}</li>
                                <li><b>Name: </b>${rd.carname}</li>
                                <li><b>Unit Price: </b>${rd.unitprice}</li>
                            </ul>
                        </td>
                        <td>${rd.quantity}</td>
                        <td>${rd.totalprice}</td>
                        <td>${rd.rentdate}</td>
                        <td>${rd.returndate}</td>
                    </tr>
                </tbody>
            </table>
            <br/>
            <table>
                <tr>
                    <td><b>Rating: </b></td>
                    <td>
                        <select name="rate">
                            <c:forEach var="i" begin="1" end="10">
                                <option>${i} / 10 </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
            <br/>
            <input type="hidden" value="${rd.rentid}" name="rentid"/>
            <input type="hidden" value="${rd.carid}" name="carid"/>
            <input type="hidden" value="${rd.rentdate}" name="rentdate"/>
            <input type="hidden" value="${rd.returndate}" name="returndate"/>
            
            <input type="submit" value="Send" onclick="return confirm('Sure? You can not change after OK.')"/>
                   
        </form>
        </c:if>
        <!-- nothing  -->
        <c:if test="${nothing == 1}">
            <h3><font color="grey">- Something went wrong -</font></h3>
        </c:if>
        </c:if>    
    </center>
    </body>
</html>
