package com.javadub1.jsprestaurant.servlets;

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

        if(!productOptional.isPresent()) {
            resp.sendRedirect("/jsprestaurant_war_exploded/product/list?orderId=" + orderIdParam);
            return;
        }
        req.setAttribute("orderIdAttribute", orderIdParam);
        req.setAttribute("productAttribute", productOptional.get());

        req.getRequestDispatcher("/jsprestaurant_war_exploded/product-form.jsp").forward(req, resp);

    }
}
