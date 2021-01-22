import mapy.Paczkamat;
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
            cfg.configure("hibernate.cfg.xml"); //hibernate config xml file name
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


}
