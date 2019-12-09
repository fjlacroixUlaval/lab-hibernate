package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate;

import java.util.Collection;
import java.util.List;

import ca.ulaval.glo4002.garage.domain.appointments.Appointment;
import ca.ulaval.glo4002.garage.domain.orders.Order;
import ca.ulaval.glo4002.garage.domain.orders.OrderRepository;

import javax.persistence.EntityManager;

public class HibernateOrderRepository implements OrderRepository {
    @Override
    public void save(Order order) {
        EntityManager entityManager = LocalEntityManager.getEntityManager();
        entityManager.persist(order);

    }

    @Override
    public List<Order> findAll() {
        EntityManager entityManager = LocalEntityManager.getEntityManager();
        List<Order> resultList = entityManager.createQuery("SELECT o FROM orders o").getResultList();
        return resultList;
    }
}
