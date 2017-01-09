package repository;

import model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * SessionFactory singleton class used to return the isntance of the properly configured session factory
 */
public class DefaultSessionFactory {
    private static SessionFactory instance;

    public DefaultSessionFactory() {
        instance = null;
    }

    public static SessionFactory getInstance() {
        if(instance == null) {
            instance = new Configuration()
                    .configure()
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Item.class)
                    .addAnnotatedClass(Order.class)
                    .addAnnotatedClass(Tables.class)
                    .addAnnotatedClass(Notification.class)
                    .addAnnotatedClass(Payment.class)
                    .buildSessionFactory();
        }

        return instance;
    }

    public static void close() {
        if(instance != null) {
            instance.close();
            instance = null;
        }
    }

}
