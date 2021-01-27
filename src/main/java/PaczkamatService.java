import entities.Customer;
import entities.Order;
import entities.Paczkamat;
import entities.Stash;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class PaczkamatService {
    private static SessionFactory factory;
    private static Session session;
    private static Transaction tx = null;

    public PaczkamatService(String username, String password) {
        try {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate/hibernate.cfg.xml"); //hibernate config xml file name
            cfg.getProperties().setProperty("hibernate.connection.password", password);
            cfg.getProperties().setProperty("hibernate.connection.username", username);
            factory = cfg.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


    public List<Paczkamat> getAllPaczkamats() {
        List<Paczkamat> paczkamats = new ArrayList<>();

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            paczkamats = (List<Paczkamat>) session.createQuery("FROM Paczkamat").list();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return paczkamats;
    }

    public List<Order> getAllOrders() {
        List<Order> order = new ArrayList<>();

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            order = (List<Order>) session.createQuery("FROM Order").list();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return order;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            customers = (List<Customer>) session.createQuery("FROM Customer").list();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return customers;
    }

    public List<Stash> getAllStashes() {
        List<Stash> stashes = new ArrayList<>();

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            stashes = (List<Stash>) session.createQuery("FROM Stash ").list();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return stashes;
    }

    public Customer getLoggedInUser(String login, String password) {
        Customer customer = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            customer = (Customer) session.createQuery("FROM Customer customer where " +
                    "customer.login like :login and customer.password like :password").
                    setParameter("login", login).setParameter("password", password).getSingleResult();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return customer;
    }


    public <T> void insertEntity(T entity) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


}
