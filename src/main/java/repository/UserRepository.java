package repository;


import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utility.Hasher;
import utility.RMSException;

/**
 * Created by admir on 09.12.2016..
 */
public class UserRepository {
    private SessionFactory sessionFactory;

    /**
     * Dependency injection
     * @param factory
     */
    public UserRepository(SessionFactory factory) {
        sessionFactory = factory;
    }

    /**
     * Creates a User in the db and returns its ID if successful
     * @param user
     * @return
     */
    public Integer createUser(User user) {

        // Obratite paznju na ovo, ovo je vjerovatno najbitnija stvar
        // rad sa bazom mada cu za to ja vecinom bit zaduzen
        // najbitnije dvije klase za rad sa bazom u hibernate
        // su Session i Transaction
        // Session je neka vrsta lake konekcije s bazom
        // a transakcija je bilo koja promjena u bazi
        // sve manipulacije podataka u bazi idu na ovaj fazon znaci
        // napravite session sa session factory (stvorite konekciju)
        // spucate try catch i u njemu zapocnete transakciju
        // u catch tj. u slucaju da je doslo do errora
        // provjerite da li je transakcija idalje null
        // jer ako nije znaci da je doslo do errora i da se desila
        // neka transakcija(promjena), a posto je error zelimo da
        // sve vratimo na staro pa pozovemo rollback() metodu na transakciji
        // i isprintate stack trace standardno
        // jako bitno je da zatvorite session u finally block

        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer id = null;
        try {
            transaction = session.beginTransaction();
            id = (Integer) session.save(user);
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

    /**
     * Returns the record in form of User with the given id, if none is found then returns null
     * @param id
     * @return
     */
    public User readUser(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        User user = null;
        try {
            transaction = session.beginTransaction();
            user = session.get(User.class, id);
            transaction.commit();
        }
        catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return user;
    }

    /**
     * Returns the record in form of User with the given login credentials, if none is found then returns null
     * @param email
     * @param password
     * @return
     */
    public User readUserByCredentials(String email, String password) throws RMSException {
        User user = null;
        try {
            String hash = Hasher.toMD5(password);

            Session session = sessionFactory.openSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                user = (User) session.createQuery(String.format("from User U where U.password = '%s' and U.email = '%s'", hash, email)).getSingleResult();
                transaction.commit();
            }
            catch (HibernateException e) {
                if(transaction != null) transaction.rollback();
            }
            finally {
                session.close();
            }

        }
        catch (Exception e) {
            throw new RMSException("Query failed");
        }
        return user;
    }
}
