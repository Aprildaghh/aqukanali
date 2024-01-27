package Dao;

import Entity.IntentionContentEntity;
import Entity.IntentionEntity;
import org.hibernate.SessionFactory;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class SessionFactoryMaker {
    private static SessionFactory factory;

    private static void configureFactory()
    {

        Map<String, Object> settings = new HashMap<>();
        settings.put("connection.driver_class", "com.mysql.jdbc.Driver");
        settings.put("dialect", "org.hibernate.dialect.MySQL8Dialect");
        settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/aqukanali");
        settings.put("hibernate.connection.username", "root");
        settings.put("hibernate.connection.password", "zxcasd45");
        settings.put("hibernate.current_session_context_class", "thread");
        settings.put("hibernate.show_sql", "false");
        settings.put("hibernate.format_sql", "false");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(IntentionContentEntity.class);
        metadataSources.addAnnotatedClass(IntentionEntity.class);
        Metadata metadata = metadataSources.buildMetadata();

        factory = metadata.getSessionFactoryBuilder().build();


    }

    public static SessionFactory getFactory() {
        if(factory == null) configureFactory();

        return factory;
    }
}
