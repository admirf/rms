package repository;

import model.Item;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.NotYetImplementedException;
import utility.Logger;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admir on 05.01.2017..
 */
public class ItemRepository implements IRepository<Item> {

    private SessionFactory sessionFactory;

    public ItemRepository(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    /**
     * @param item
     * @return ID of newly created Item entity
     */
    @Override
    public Integer create(Item item) {
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
     * @return List of all items from database
     */
    public List<Item> readAllItems() {
        List<Item> li = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            li = session.createQuery(String.format("from Item I")).list();
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
    public Item read(Integer i) {
        throw new NotYetImplementedException();
    }

    /**
     * @param item Item to be updated
     * @return ID of updated item
     */
    @Override
    public Integer update(Item item) {
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
     * @param i
     * @return true if deletion was successful, false otherwise
     */
    @Override
    public boolean delete(Integer i) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery(String.format("delete Item U where U.ID = %d", i)).executeUpdate();
            transaction.commit();
        }
        catch(HibernateException e) {
            if(transaction != null) transaction.rollback();
            return false;
        }
        finally {
            session.close();
        }
        return true;
    }
}


