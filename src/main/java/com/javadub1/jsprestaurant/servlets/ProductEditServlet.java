package com.javadub1.jsprestaurant.servlets;

import com.javadub1.jsprestaurant.model.Order;
import com.javadub1.jsprestaurant.model.Product;
import com.javadub1.jsprestaurant.services.OrderService;
import com.javadub1.jsprestaurant.services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/product/edit")
public class ProductEditServlet extends HttpServlet {

    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdParam = req.getParameter("orderId");
        String productIdParam = req.getParameter("productId");

        Long productId = Long.parseLong(productIdParam);
        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()) {
            resp.sendRedirect("/product/list?orderId=" + orderIdParam);
            return;
        }
        req.setAttribute("orderIdAttribute", orderIdParam);
        req.setAttribute("productAttribute", productOptional.get());

        req.getRequestDispatcher("/product-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        OrderService orderService = new OrderService();

        String orderIdString = req.getParameter("orderId");
        String amountString = req.getParameter("amountParam");
        String valueString = req.getParameter("valueParam");
        String description = req.getParameter("descriptionParam");
        String editedProductIdString = req.getParameter("editedProductId");


        Long orderId = Long.parseLong(orderIdString);
        Long editedProductId = Long.parseLong(editedProductIdString);
        Integer amount = Integer.parseInt(amountString);
        Double value = Double.parseDouble(valueString);

        Product product = new Product();
        product.setId(editedProductId);
        product.setAmount(amount);
        product.setValue(value);
        product.setDescription(description);

        Optional<Order> orderOptional = orderService.findById(orderId);
        if(!orderOptional.isPresent()) {
            resp.sendRedirect("/jsprestaurant_war_exploded/order/list");
            return;
        }
        product.setOrder(orderOptional.get());
        productService.update(product);

        resp.sendRedirect("/jsprestaurant_war_exploded/product/list?orderId=" + orderIdString);
    }
}