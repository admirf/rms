package repository;

import model.Tables;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.NotYetImplementedException;
import utility.Logger;

import java.util.List;

/**
 * Repository of the Table entity
 */
public class TablesRepository implements IRepository<Tables> {
    private SessionFactory sessionFactory;

    public TablesRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Creates a table in the database
     * @param tables
     * @return The id of the creted table
     */
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
            e.printStackTrace(Logger.getInstance().getWriter());
        }
        finally {
            session.close();
        }
        return id;
    }

    /**
     * @param name
     * @return The table which corresponds to given name
     */
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
            e.printStackTrace(Logger.getInstance().getWriter());
        }
        finally {
            session.close();
        }
        return table;
    }

    /**
     * Switches the tables occupied field from true to false and vice versa
     * @param name
     * @return True if no error occurred, false otherwise
     */
    public boolean toggleOccupied(Integer name) {
        Tables table = readByName(name);
        if(table == null) return false;

        table.setOccupied(!table.isOccupied());
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(table);
            transaction.commit();
        }
        catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            return false;
        }
        finally {
            session.close();
        }

        return true;
    }

    /**
     * @return List of all tables
     */
    public List<Tables> readAllTables() {
        List<Tables> li = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            li = session.createQuery(String.format("from Tables U")).list();

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
