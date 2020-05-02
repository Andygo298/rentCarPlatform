package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SFUtil {
    private static final SessionFactory sessionFactory;

    /*
            SessionFactory initialization
    */
    static {
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        // Hibernate settings equivalent to hibernate.cfg.xml's properties
        Map<String, String> settings = new HashMap<>();
        ResourceBundle resource = ResourceBundle.getBundle("db");
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, resource.getString("url"));
        settings.put(Environment.USER, resource.getString("user"));
        settings.put(Environment.PASS, resource.getString("password"));
        settings.put(Environment.DIALECT,resource.getString("mySQL_5"));
        settings.put(Environment.HBM2DDL_AUTO, resource.getString("hbm2ddl_auto_type"));
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.USE_SQL_COMMENTS, "false");
        settings.put(Environment.FORMAT_SQL, "false");
        settings.put(Environment.ISOLATION, "2");
        settings.put(Environment.STORAGE_ENGINE, "innodb");
        settings.put(Environment.C3P0_MIN_SIZE, "5");
        settings.put(Environment.C3P0_MAX_SIZE, "20");
        settings.put(Environment.C3P0_ACQUIRE_INCREMENT, "1");
        settings.put(Environment.C3P0_TIMEOUT, "1800");
        settings.put(Environment.C3P0_MAX_STATEMENTS, "150");
        settings.put(Environment.C3P0_CONFIG_PREFIX + ".initialPoolSize", "5");
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class)
                .addAnnotatedClass(AuthUser.class)
                .addAnnotatedClass(Car.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Payment.class);
        // Apply settings
        serviceRegistryBuilder.applySettings(settings);
        // Create registry
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        // Create MetadataSources
        MetadataSources sources = new MetadataSources(serviceRegistry);
        sources.addAnnotatedClass(User.class)
                .addAnnotatedClass(AuthUser.class)
                .addAnnotatedClass(Car.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Payment.class);
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