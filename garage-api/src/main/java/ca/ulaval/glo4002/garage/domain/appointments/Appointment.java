package ca.ulaval.glo4002.garage.domain.appointments;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="appointments")
@Table
public class Appointment implements Serializable {
    public Appointment() {
        this.appointmentNumber = null;
        this.clientName = null;
        this.clientPhone = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private final AppointmentNumber appointmentNumber;

    @Column
    private final String clientName;
    @Column
    private final String clientPhone;

    public Appointment(AppointmentNumber appointmentNumber, String clientName, String clientPhone) {
        this.appointmentNumber = appointmentNumber;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public AppointmentNumber getAppointmentNumber() {
        return appointmentNumber;
    }
}
