package repository;

import model.Order;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.NotYetImplementedException;

/**
 * Created by admir on 08.01.2017..
 */
public class OrderRepository implements IRepository<Order> {
    private SessionFactory sessionFactory;

    public OrderRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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
            e.printStackTrace();
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

    @Override
    public boolean delete(Integer i) {
        throw new NotYetImplementedException();
    }

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
}
