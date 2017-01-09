package repository;

import model.Order;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.NotYetImplementedException;
import utility.Logger;

import java.util.List;

/**
 * Created by admir on 08.01.2017..
 */
public class OrderRepository implements IRepository<Order> {
    private SessionFactory sessionFactory;

    public OrderRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @param order
     * @return ID of newly created order
     */
    @Override
    public Integer create(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer id = null;
        try {
            transaction = session.beginTransaction();
            id = (Integer) session.save(order);
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

    @Override
    public Order read(Integer i) {
        throw new NotYetImplementedException();
    }

    @Override
    public Integer update(Order order) {
        throw new NotYetImplementedException();
    }

    /**
     * @param i
     * @return true if the deletion was successful, false otherwise
     */
    @Override
    public boolean delete(Integer i) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery(String.format("delete Order U where U.ID = %d", i)).executeUpdate();
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

    /**
     * Deletes an order by the table property of it
     * @param i
     * @return true if no error occured, false otherwise
     */
    public boolean deleteByTable(Integer i) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery(String.format("delete Order U where U.table = %d", i)).executeUpdate();
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

    /**
     * @return List of all orders from the database
     */
    public List<Order> readAllOrders() {
        List<Order> li = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            li = session.createQuery(String.format("from Order U")).list();
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
}
