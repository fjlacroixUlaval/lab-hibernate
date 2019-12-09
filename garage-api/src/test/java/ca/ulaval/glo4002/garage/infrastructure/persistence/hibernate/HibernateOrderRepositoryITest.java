package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate;

import ca.ulaval.glo4002.garage.domain.appointments.Appointment;
import ca.ulaval.glo4002.garage.domain.appointments.AppointmentFactory;
import ca.ulaval.glo4002.garage.domain.orders.Order;
import ca.ulaval.glo4002.garage.domain.orders.OrderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HibernateOrderRepositoryITest {

    private Appointment appointment;
    private Appointment anotherAppointment;
    private AppointmentFactory appointmentFactory;
    private Order order;
    private Order anotherOrder;
    private OrderFactory orderFactory;
    private HibernateOrderRepository hibernateOrderRepository;
    private EntityManager entityManager;

    @BeforeEach
    void setUp(){
        appointmentFactory = new AppointmentFactory();
        appointment = appointmentFactory.create("A Client", "123-456-7890");
        anotherAppointment = appointmentFactory.create("Another Client", "098-765-4321");

        orderFactory = new OrderFactory();
        order = orderFactory.create(appointment.getAppointmentNumber(),"Bumper");
        anotherOrder = orderFactory.create(anotherAppointment.getAppointmentNumber(),"Wind shield");

        hibernateOrderRepository = new HibernateOrderRepository();

        ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.EntityManagerFactory.getInstance();
        entityManager = EntityManagerFactory.getInstance().createEntityManager();
        LocalEntityManager.setEntityManager(entityManager);
    }

    @Test
    void findAll_returnsAllSavedOrders() {
        entityManager.getTransaction().begin();
        hibernateOrderRepository.save(order);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        hibernateOrderRepository.save(anotherOrder);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        List<Order> allOrders = hibernateOrderRepository.findAll();

        assertTrue(allOrders.contains(order));
        assertTrue(allOrders.contains(anotherOrder));
        entityManager.close();
    }
}