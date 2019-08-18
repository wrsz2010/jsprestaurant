<%@ page import="com.javadub1.jsprestaurant.model.Product" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.javadub1.jsprestaurant.model.Order" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: wrsz2
  Date: 17/08/2019
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product List</title>
    <%
        Long orderId = Long.parseLong(request.getParameter("orderId"));
    %>
    <a href="/jsprestaurant_war_exploded/product/add?orderId=<%=orderId%>"> Dodaj produkt</a>
</head>
<body>
<table width="100%" border="1">
    <tr>
        <th>Id</th>
        <%--<th>Ilość produktów:</th>--%>
        <th>Opis:</th>
        <th>Value:</th>
        <th>Amount:</th>
        <th>Akcje:</th>
    </tr>
    <%


        Set<Product> productList = (Set<Product>) request.getAttribute("orderProductAttribute");
        for (Product product : productList) {
            out.print("<tr>");
            out.print("<td>" + product.getId() + "</td>");
            out.print("<td>" + product.getDescription() + "</td>");
            out.print("<td>" + product.getValue() + "</td>");
            out.print("<td>" + product.getAmount() + "</td>");
            out.print("<td>" +
                    getLinkForProductRemove(orderId, product.getId()) +
                    "</td>");
            out.print("</tr>");
        }
%>

</table>
<jsp:include page="navigator.jsp"/>
</body>
</html>
<%!
    private String getLinkForProductRemove(Long orderId, Long productId) {
    return "<a href=\"/jsprestaurant_war_exploded/product/remove?orderId=" + orderId + "&productId=" + productId + "\">Usun produkt</a>";
    }

%>