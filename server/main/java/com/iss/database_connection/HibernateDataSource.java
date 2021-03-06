package com.iss.database_connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Component;

@Component
public class HibernateDataSource {
    private SessionFactory sessionFactory;

    public Session getSession() {
        initialize();
        return sessionFactory.openSession();
    }

    void initialize() {
        if (sessionFactory != null){
            return;
        }
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).
                    buildMetadata().
                    buildSessionFactory();
        }
        catch (Exception e) {
            System.out.println("Exceptie "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    @Override
    protected void finalize() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }
}
