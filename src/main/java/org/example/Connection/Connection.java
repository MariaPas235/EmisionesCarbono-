package org.example.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connection {
    private static Connection instance = new Connection();
    private SessionFactory sessionFactory;

    private Connection() {
        try{
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    public void close() {
        if (sessionFactory != null && sessionFactory.isOpen())  {
            sessionFactory.close();
        }

    }

    public Session openSession(){
        return sessionFactory.openSession();
    }
}


