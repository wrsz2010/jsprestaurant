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
            out.print("<td>" + String.format("%.2f", order.calculateToPay() + "</td>");
            out.print("<td>" + (order.getPaid() != null) + "</td>");
            out.print("<td>" +
                    getLinkForProductList(order.getId()) +
                    getLinkForDeliveringOrder(order) +
                    getLinkPaidOrder(order) +
                    getLinkToRemoveOrder(order) +
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
    private String getLinkForDeliveringOrder(Order order) {
        if (order.getTimeDelivered() == null) {
            return "<a href=\"/jsprestaurant_war_exploded/order/deliver?orderId=" + order.getId() + "\">Dostarczono</a>";
        } else {
            return "";
        }
    }

    private String getLinkPaidOrder(Order order) {
        if (order.getTimeDelivered() != null && order.getPaid() == null && order.getToPay() != null) {
            return "<a href=\"/jsprestaurant_war_exploded/order/paid?orderId=" + order.getId() + "\">Zaplacono</a>";
        } else {
            return "";
        }
    }
    private String getLinkToRemoveOrder(Order order) {
        if (order.getProductsCount() == 0) {
            return "<a href=\"/jsprestaurant_war_exploded/order/remove?orderId=" + order.getId() + "\">Usun</a>";
        } else {
            return "";
        }
    }
%>