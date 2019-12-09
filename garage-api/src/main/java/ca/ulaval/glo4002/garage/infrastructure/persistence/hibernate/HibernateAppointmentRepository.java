package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.garage.domain.appointments.Appointment;
import ca.ulaval.glo4002.garage.domain.appointments.AppointmentNumber;
import ca.ulaval.glo4002.garage.domain.appointments.AppointmentRepository;
import ca.ulaval.glo4002.garage.domain.appointments.DuplicateAppointmentException;

import javax.persistence.EntityManager;

public class HibernateAppointmentRepository implements AppointmentRepository {
    @Override
    public void save(Appointment appointment) {
       EntityManager entityManager = LocalEntityManager.getEntityManager();

        Optional<Appointment> duplicateAppointment = findByNumber(appointment.getAppointmentNumber());

        if(duplicateAppointment.isPresent()){
            entityManager.getTransaction().setRollbackOnly();
            throw new DuplicateAppointmentException();
        }

        entityManager.persist(appointment);

    }

    @Override
    public Collection<Appointment> findAll() {
        EntityManager entityManager = LocalEntityManager.getEntityManager();
        Collection<Appointment> resultList = entityManager.createQuery("SELECT a FROM appointments a").getResultList();
        return resultList;
    }

    @Override
    public Optional<Appointment> findByNumber(AppointmentNumber appointmentNumber) {
        Collection<Appointment> allAppointments = findAll();
        Optional<Appointment> optionalAppointment = allAppointments.stream().filter(app -> app.getAppointmentNumber().equals(appointmentNumber)).findAny();

        return optionalAppointment;
    }
}
