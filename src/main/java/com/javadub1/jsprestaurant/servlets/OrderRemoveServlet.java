package com.javadub1.jsprestaurant.servlets;

import com.javadub1.jsprestaurant.model.Order;
import com.javadub1.jsprestaurant.services.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order/remove")
public class OrderRemoveServlet extends HttpServlet {

    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdString = req.getParameter(("orderId"));
        Long orderId = Long.parseLong(orderIdString);



        orderService.delete(orderId);

        String referer = req.getHeader("referer");
        resp.sendRedirect(referer);
    }
}
