package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class SFUtil {
    private static final SessionFactory sessionFactory;

    /*
            SessionFactory initialization
    */
    static {
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        // Hibernate settings equivalent to hibernate.cfg.xml's properties
        Map<String, String> settings = new HashMap<>();
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, "jdbc:mysql://127.0.0.1:3306/dc?serverTimezone=UTC");
        settings.put(Environment.USER, "root");
        settings.put(Environment.PASS, "root");
        settings.put(Environment.HBM2DDL_AUTO, "create-drop");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.USE_SQL_COMMENTS, "false");
        settings.put(Environment.FORMAT_SQL, "false");
        settings.put(Environment.ISOLATION, "2");
        settings.put(Environment.STORAGE_ENGINE, "innodb");
        // Apply settings
        serviceRegistryBuilder.applySettings(settings);
        // Create registry
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        // Create MetadataSources
        MetadataSources sources = new MetadataSources(serviceRegistry);
        sources.addAnnotatedClass(User.class);
        // Create Metadata
        Metadata metadata = sources.getMetadataBuilder().build();
        // Create SessionFactory
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}