package com.javadub1.jsprestaurant.servlets;

import com.javadub1.jsprestaurant.model.Order;
import com.javadub1.jsprestaurant.services.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/order/list")
public class OrderListServlet extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orderList = orderService.findAll();

        req.setAttribute("orderListAttribute", orderList);

        req.getRequestDispatcher("/order-list.jsp").forward(req, resp);
    }

}
