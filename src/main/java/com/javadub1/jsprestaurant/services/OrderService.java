package com.javadub1.jsprestaurant.services;


import com.javadub1.jsprestaurant.database.EntityDao;
import com.javadub1.jsprestaurant.database.HibernateUtil;
import com.javadub1.jsprestaurant.model.Order;
import com.javadub1.jsprestaurant.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private EntityDao entityDao = new EntityDao();

    public void add(Order order) {
        entityDao.saveOrUpdate(order);
    }

    public void delete(Long orderId) {

        Order order = entityDao.getById(Order.class, orderId);
        entityDao.delete(order);
    }

    public List<Order> findAll() {
        return entityDao.list(Order.class);
    }

    public void delivered(Long id) {
        Order delivered = entityDao.getById(Order.class, id); // pobierz do edycji
        delivered.setTimeDelivered(LocalDateTime.now()); // ustaw zmieniony

        entityDao.saveOrUpdate(delivered); // zapisz w bazie zmieniony
    }

    public void paid(Long id, Double amount) {
//                pobieramy z bazy obiekt Order ustawiamy mu kwote zapłaconą
        Order order = entityDao.getById(Order.class, id); // pobierz do edycji
        order.setPaid(amount);

//                jeśli kwota jest za niska, wypisz komunikat (System.err...) o zapłaconej zbyt niskiej kwocie.
//                ...
        if (order.getPaid() < order.getToPay()) {
            System.err.println("Ty brzydalu nie zapłaciłeś całości pieniędzy.");
        } else if (order.getPaid() == order.getToPay()) {
            System.err.println("Ty brzydalu nie dałeś napifka.");
        }

//                zapisz obiekt z powrotem do bazy
        entityDao.saveOrUpdate(order);
    }

    public void addProduct(Long orderId, Product product) {
        Order orderToWhichProductShallBeAdded = entityDao.getById(Order.class, orderId);

        product.setOrder(orderToWhichProductShallBeAdded);
        entityDao.saveOrUpdate(product); // najpierw musimy mieć pewność że produkt jest w bazie

//        Dwie poniższe linie są w pełni opcjonalne!
        orderToWhichProductShallBeAdded.getProducts().add(product);
        entityDao.saveOrUpdate(orderToWhichProductShallBeAdded); // zapisujemy order powiązany z produktem.
    }

    public Optional<Order> findById(Long idOrder) {

        Order order = entityDao.getById(Order.class, idOrder);
        return Optional.ofNullable(order);
    }

    public void removeProduct(Long idOrder, Long idProduct) {

        Order order = entityDao.getById(Order.class, idOrder);
        Product product = entityDao.getById(Product.class, idProduct);

        if(product != null) {
            order.getProducts().remove(product);
            product.setOrder(null);
            entityDao.saveOrUpdate(order);
            entityDao.delete(product);
        } else {
            System.err.println("Nie ma takiego produktu");
        }
    }

    // pobierz orders ktore sa undelivered
    public List<Order> listUndelivered() {
        List<Order> orderList = new ArrayList<>();

        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            // budowniczy zapytania
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // tworzymy obiekt zawierający kryteria zapytania O OBIEKT Order
            CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);

            // tabela w której będziemy wyszukiwać
            Root<Order> table = criteriaQuery.from(Order.class);

            // wykonaj zapytanie na tabeli table, użyj kryteriów "criteria query"
            criteriaQuery.select(table)
                    .where(
                            builder.isNull(table.get("timeDelivered"))
            );

            // wykonaj zapytanie na bazie i wyniki dopisz do listy
            orderList.addAll(session.createQuery(criteriaQuery).list());
        }

        return orderList;
    }

    public List<Order> listUnpaid() {
        List<Order> orderList = new ArrayList<>();

        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            // budowniczy zapytania
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // tworzymy obiekt zawierający kryteria zapytania O OBIEKT Order
            CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);

            // tabela w której będziemy wyszukiwać
            Root<Order> table = criteriaQuery.from(Order.class);

            // wykonaj zapytanie na tabeli table, użyj kryteriów "criteria query"
            criteriaQuery.select(table)
                    .where(
                            builder.isNull(table.get("paid"))
                    );

            // wykonaj zapytanie na bazie i wyniki dopisz do listy
            orderList.addAll(session.createQuery(criteriaQuery).list());
        }

        return orderList;
    }

    public Double sumPaid() {

        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            // budowniczy zapytania
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // tworzymy obiekt zawierający kryteria zapytania O OBIEKT Order
            CriteriaQuery<Double> criteriaQuery = builder.createQuery(Double.class);

            // tabela w której będziemy wyszukiwać
            Root<Order> table = criteriaQuery.from(Order.class);

            // wykonaj zapytanie na tabeli table, użyj kryteriów "criteria query"

            LocalDate today = LocalDate.now();
            criteriaQuery
                    .select(
                            builder.sum(table.get("paid"))
                    )
                    .where(
                            builder.between(
                                    table.get("timeOrdered"),
                                   today.atStartOfDay(),
                                   today.plusDays(1).atStartOfDay()
                                   // today.atTime(9, 0),
                                   // today.atTime(18, 0)
                            )
                    );

            // wykonaj zapytanie na bazie i wyniki dopisz do listy
            return session.createQuery(criteriaQuery).getSingleResult();
        }
    }

    public void paid(Long id) {
        Order order = entityDao.getById(Order.class, id);
        if(order != null) {
            order.setPaid(order.getToPay());
            entityDao.saveOrUpdate(order);
        } else {
            System.err.println("Nie ma takiego orderu !!!");
        }
    }

    public List<Order> listCurrent() {
        List<Order> orderList = new ArrayList<>();

        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            // budowniczy zapytania
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // tworzymy obiekt zawierający kryteria zapytania O OBIEKT Order
            CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);

            // tabela w której będziemy wyszukiwać
            Root<Order> table = criteriaQuery.from(Order.class);

            // wykonaj zapytanie na tabeli table, użyj kryteriów "criteria query"

            LocalDate today = LocalDate.now();
            criteriaQuery
                    .select(table)

                    .where(
                            builder.and(
                                    builder.between(
                                    table.get("timeOrdered"),
                                    today.atStartOfDay(),
                                    today.plusDays(1).atStartOfDay()
                                    // today.atTime(9, 0),
                                    // today.atTime(18, 0)
                            ),
                                    builder.isNull(
                                            table.get("paid")
                                    )
                            )
                    );

            orderList.addAll(session.createQuery(criteriaQuery).list());
            // wykonaj zapytanie na bazie i wyniki dopisz do listy
            return orderList;
        }
    }
}
