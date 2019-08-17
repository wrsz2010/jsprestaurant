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
import java.util.Set;

@WebServlet("/product/list")
public class ProductListServlet extends HttpServlet {

    private OrderService orderService = new OrderService();
    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdString = req.getParameter("orderId");
        Long orderId = Long.parseLong(orderIdString);

        Set<Product> productSet = productService.findProductsByOrderId(orderId);
  //      for(Product product : productSet) {
  //          System.out.println(product);
 //       }

        req.setAttribute("orderProductAttribute", productSet);

        req.getRequestDispatcher("/product-list.jsp").forward(req, resp);

    }
}
