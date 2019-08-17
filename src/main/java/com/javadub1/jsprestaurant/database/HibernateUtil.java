package com.javadub1.jsprestaurant.database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        // załaduje się jeden raz przy załadowaniu klasy
        // Tworzymy sobie obiekt, który pobiera konfigurację z pliku hibernate cfg xml
        StandardServiceRegistry standardServiceRegistry = // obiekt podobny do properties, tylko ładuje XML
                new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml").build();

        // metadata to informacje dotyczące plików. z danych załadowanych wcześniej
        // towrzymy sobie obiekt metadata.
        Metadata metadata = new MetadataSources(standardServiceRegistry)
                .getMetadataBuilder().build();

        // stworzenie sesji z danych zawartych w pliku hibernate cfg xml
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
