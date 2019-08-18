package com.javadub1.jsprestaurant.services;

import com.javadub1.jsprestaurant.database.EntityDao;
import com.javadub1.jsprestaurant.database.HibernateUtil;
import com.javadub1.jsprestaurant.model.Order;
import com.javadub1.jsprestaurant.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProductService {

    private EntityDao entityDao = new EntityDao();

    public Set<Product> findProductsByOrderId(Long orderId) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            Order result = session.get(Order.class, orderId);
            return new HashSet<>(result.getProducts());
        }
    }

    public Optional<Product> findById(Long productId) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            Product result = session.get(Product.class, productId);
            return Optional.ofNullable(result);
        }
    }

    public void update(Product product) {
        entityDao.saveOrUpdate(product);
    }
}
