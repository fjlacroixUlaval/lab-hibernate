package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate;

import ca.ulaval.glo4002.garage.domain.appointments.Appointment;
import ca.ulaval.glo4002.garage.domain.appointments.AppointmentFactory;
import ca.ulaval.glo4002.garage.domain.appointments.AppointmentNumber;
import ca.ulaval.glo4002.garage.domain.appointments.DuplicateAppointmentException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HibernateAppointmentRepositoryITest {

    private Appointment appointment;
    private Appointment anotherAppointment;
    private AppointmentNumber appointmentNumber;
    private AppointmentFactory appointmentFactory;
    private HibernateAppointmentRepository hibernateAppointmentRepository;
    private EntityManager entityManager;

    @BeforeEach
    void setUp(){
        appointmentFactory = new AppointmentFactory();
        appointment = appointmentFactory.create("A Client", "123-456-7890");
        anotherAppointment = appointmentFactory.create("Another Client", "098-765-4321");

        ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.EntityManagerFactory.getInstance();
        entityManager = EntityManagerFactory.getInstance().createEntityManager();
        LocalEntityManager.setEntityManager(entityManager);
        hibernateAppointmentRepository = new HibernateAppointmentRepository();

    }

    @AfterEach
    void finish(){
        hibernateAppointmentRepository = null;
        entityManager.close();
        entityManager = null;
        EntityManagerFactory.reset();
    }

    @Test
    void saveAppointment_canThenRetrieveItByNumber() {
        entityManager.getTransaction().begin();
        hibernateAppointmentRepository.save(appointment);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Optional<Appointment> theSameAppointment = hibernateAppointmentRepository.findByNumber(appointment.getAppointmentNumber());

        assertEquals(appointment, theSameAppointment.get());
    }

    @Test
    void saveAppointment_createsAnErrorWhenAnAppointmentIsSavedTwiceWithTheSameNumber() {
        entityManager.getTransaction().begin();
        hibernateAppointmentRepository.save(appointment);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        assertThrows(DuplicateAppointmentException.class,()->{hibernateAppointmentRepository.save(appointment);});
    }

    @Test
    void saveTwoAppointments_canRetrieveAListOfAppointments() {
        entityManager.getTransaction().begin();
        hibernateAppointmentRepository.save(appointment);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        hibernateAppointmentRepository.save(anotherAppointment);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Collection<Appointment> appointments = hibernateAppointmentRepository.findAll();

        assertTrue(appointments.contains(appointment));
        assertTrue(appointments.contains(anotherAppointment));
    }

    @Test
    void findAppointmentByNumber_resultIsEmptyIfAppointmentDoesNotExist() {
        entityManager.getTransaction().begin();
        Optional<Appointment> emptyOptional = hibernateAppointmentRepository.findByNumber(appointment.getAppointmentNumber());

        assertFalse(emptyOptional.isPresent());
    }
}