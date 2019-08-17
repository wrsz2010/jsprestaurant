<%@ page import="com.javadub1.jsprestaurant.model.Order" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: amen
  Date: 8/17/19
  Time: 1:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order List</title>
</head>
<body>
<h1>Lista zamówień</h1>
<table width="100%" border="1">
    <tr>
        <th>Id</th>
        <%--<th>Ilość produktów:</th>--%>
        <th>Czas złożenia zamówienia:</th>
        <th>Czas dostarczenia:</th>
        <th>Ilość osób:</th>
        <th>Numer stolika:</th>
        <th>Do zapłaty:</th>
        <th>Czy zapłacono:</th>
        <th>Akcje:</th>
    </tr>
    <%
        List<Order> orderList = (List<Order>) request.getAttribute("orderListAttribute");
        for (Order order : orderList) {
            out.print("<tr>");
            out.print("<td>" + order.getId() + "</td>");
//            out.print("<td>"+order.getProducts().size()+"</td>");
            out.print("<td>" + order.getFormattedTimeOrdered() + "</td>");
            out.print("<td>" + order.getFormattedTimeDelivered() + "</td>");
            out.print("<td>" + order.getPeopleCount() + "</td>");
            out.print("<td>" + order.getTableNumber() + "</td>");
            out.print("<td>" + (order.calculateToPay()) + "</td>");
            out.print("<td>" + (order.getPaid() != null) + "</td>");
            out.print("<td>" +
                    getLinkForProductList(order.getId()) +
                    "</td>");
            out.print("</tr>");
        }
    %>
</table>
<jsp:include page="navigator.jsp"/>
</body>
</html>
<%!
    private String getLinkForProductList(Long id) {
        return "<a href=\"/jsprestaurant_war_exploded/product/list?orderId=" + id + "\">Produkty</a>";
    }
%>