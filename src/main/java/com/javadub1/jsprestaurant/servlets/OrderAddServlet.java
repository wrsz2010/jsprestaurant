package com.javadub1.jsprestaurant.servlets;

import com.javadub1.jsprestaurant.model.Order;
import com.javadub1.jsprestaurant.services.OrderService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order/add")
public class OrderAddServlet extends HttpServlet {

    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/order-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String peopleCountString = req.getParameter("peopleCountParam");
        String tableNumberString = req.getParameter("tableNumberParam");

        int peopleCount = Integer.parseInt(peopleCountString);
        int tableNumber = Integer.parseInt(tableNumberString);

        Order order = new Order();

        order.setPeopleCount(peopleCount);
        order.setTableNumber(tableNumber);

        orderService.add(order);

        resp.sendRedirect("/jsprestaurant_war_exploded/order/list");
    }
}
