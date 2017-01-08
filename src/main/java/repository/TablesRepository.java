package repository;

import model.Tables;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.NotYetImplementedException;

/**
 * Created by admir on 08.01.2017..
 */
public class TablesRepository implements IRepository<Tables> {
    private SessionFactory sessionFactory;

    public TablesRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer create(Tables tables) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer id = null;
        try {
            transaction = session.beginTransaction();
            id = (Integer) session.save(tables);
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

    public Tables readByName(Integer name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Tables table = null;
        try {
            transaction = session.beginTransaction();
            table = (Tables) session.createQuery(String.format("from Tables T where T.name = '%d'", name)).getSingleResult();
            transaction.commit();
        }
        catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return table;
    }

    @Override
    public Tables read(Integer i) {
        throw new NotYetImplementedException();
    }

    @Override
    public Integer update(Tables tables) {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean delete(Integer i) {
        throw new NotYetImplementedException();
    }
}
