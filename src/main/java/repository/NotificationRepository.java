package repository;

import model.Item;
import model.Notification;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.NotYetImplementedException;
import utility.Logger;

import java.util.List;

/**
 * Created by admir on 09.01.2017..
 */
public class NotificationRepository implements IRepository<Notification> {

    private SessionFactory sessionFactory;

    public NotificationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @param item
     * @return ID of newly created notification
     */
    public Integer create(Notification item) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer id = null;
        try {
            transaction = session.beginTransaction();
            id = (Integer) session.save(item);
            transaction.commit();
        }
        catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace(Logger.getInstance().getWriter());
        }
        finally {
            session.close();
        }
        return id;
    }

    /**
     * @param item Entity to be updated
     * @return Id of updated item
     */
    public Integer update(Notification item) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(item);
            transaction.commit();
            return item.getID();
        }
        catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
        }
        return null;
    }

    /**
     * @return List of all unread notifications from database
     */
    public List<Notification> readAllUnreadNotifications() {
        List<Notification> li = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            li = session.createQuery(String.format("from Notification I where I.checked=0")).list();
            System.out.println(li.size());
            transaction.commit();
        }
        catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
        }
        finally {
            session.close();
        }
        return li;
    }

    @Override
    public Notification read(Integer i) {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean delete(Integer i) {
        throw new NotYetImplementedException();
    }
}
