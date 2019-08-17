package com.javadub1.jsprestaurant.database;


import com.javadub1.jsprestaurant.model.IBaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EntityDao {
    public void saveOrUpdate(IBaseEntity entity) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
        } // try with resources - zamknie sesję (session.close()) automatycznie po
        // zamknięciu klamry try
    }

    public void delete(IBaseEntity entity) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } // try with resources - zamknie sesję (session.close()) automatycznie po
        // zamknięciu klamry try
    }


    public <T extends IBaseEntity> T getById(Class<T> classT, Long id) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            T result = session.get(classT, id);
            return result;
        }
    }


    public <T extends IBaseEntity> List<T> list(Class<T> classT) {
        List<T> orderList = new ArrayList<>();

        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            // budowniczy zapytania
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // tworzymy obiekt zawierający kryteria zapytania O OBIEKT Order
            CriteriaQuery<T> criteriaQuery = builder.createQuery(classT);

            // tabela w której będziemy wyszukiwać
            Root<T> table = criteriaQuery.from(classT);

            // wykonaj zapytanie na tabeli table, użyj kryteriów "criteria query"
            criteriaQuery.select(table);

            // wykonaj zapytanie na bazie i wyniki dopisz do listy
            orderList.addAll(session.createQuery(criteriaQuery).list());
        }

        return orderList;
    }

}
