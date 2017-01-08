package repository;

import model.Item;
import model.Order;
import model.User;
import org.hibernate.SessionFactory;
import model.Tables;
import org.hibernate.cfg.Configuration;

/**
 * Created by admir on 10.12.2016..
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
