package ca.ulaval.glo4002.garage.domain.orders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.garage.domain.appointments.AppointmentNumber;

import javax.persistence.*;

@Entity(name = "orders")
@Table
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private final LocalDateTime date;
    @Column
    private final AppointmentNumber referenceNumber;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private final List<Part> parts = new ArrayList<>();

    public Order(LocalDateTime date, AppointmentNumber referenceNumber) {
        this.date = date;
        this.referenceNumber = referenceNumber;
    }

    public Order() {
        this.date = null;
        this.referenceNumber = null;
    }

    public void addPart(Part part) {
        parts.add(part);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public AppointmentNumber getReferenceNumber() {
        return referenceNumber;
    }

    public List<Part> listParts() {
        return parts;
    }
}
