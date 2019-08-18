<%--
  Created by IntelliJ IDEA.
  User: wrsz2
  Date: 18/08/2019
  Time: 12:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product form</title>
</head>
<body>
<jsp:include page="navigator.jsp"/>
<h1>Formularz produktu</h1>
<form style="width: 50%;margin:auto;" action="/jsprestaurant_war_exploded/product/add" method="post">
    <input type="hidden" name="orderId" value="<%= request.getAttribute("orderIdAttribute")%>">
    <label style="display:inline-block;min-width: 49%;width: 49%;" for="descriptionId">Opis zamówienia:</label>
    <input style="width: 50%" id="descriptionId" name="descriptionParam" type="text">

    <br/>

    <label style="display:inline-block;min-width: 49%;width: 49%;" for="amountId">Ilość:</label>
    <input style="width: 50%" id="amountId" name="amountParam" type="number" step="1">

    <br/>

    <label style="display:inline-block;min-width: 49%;width: 49%;" for="valueId">Cena:</label>
    <input style="width: 50%" id="valueId" name="valueParam" type="number" step="0.01" min="0.0">

    <br/>

    <input style="width: 20%" type="submit">

</form>

</body>
</html>