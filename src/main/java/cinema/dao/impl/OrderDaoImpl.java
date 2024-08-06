package cinema.dao.impl;

import cinema.model.Order;
import cinema.model.User;
import cinema.dao.OrderDao;
import cinema.exception.DataProcessingException;
import cinema.lib.Dao;
import cinema.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order add(Order order) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(order);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error adding order" + order, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return order;
    }

    @Override
    public List<Order> getByUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select distinct o from Order o "
                            + "join fetch o.user u "
                            + "join fetch o.tickets t "
                            + "join fetch t.movieSession ms "
                            + "join fetch ms.cinemaHall ch "
                            + "join fetch ms.movie m "
                            + "where o.user = :user", Order.class)
                    .setParameter("user", user).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Error getting orders by user" + user, e);
        }
    }
}
