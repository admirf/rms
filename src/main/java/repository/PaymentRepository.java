package repository;

import model.Order;
import model.Payment;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.NotYetImplementedException;
import utility.Logger;

import java.util.Date;
import java.util.List;

/**
 * Repository of the Payment entity
 */
public class PaymentRepository implements IRepository<Payment> {
    private SessionFactory sessionFactory;

    public PaymentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @param payment
     * @return ID of newly created payment
     */
    @Override
    public Integer create(Payment payment) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer id = null;
        try {
            transaction = session.beginTransaction();
            id = (Integer) session.save(payment);
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
     * @param date
     * @return List of all payments which timestamp is newer than the given parameter
     */
    public List<Payment> readAllPaymentsByDate(Date date) {
        List<Payment> li = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            li = session.createQuery(String.format("from Payment U where U.timestamp > %s", new java.sql.Date(date.getTime()))).list();
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
    public Payment read(Integer i) {
        throw new NotYetImplementedException();
    }

    @Override
    public Integer update(Payment payment) {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean delete(Integer i) {
        throw new NotYetImplementedException();
    }
}
