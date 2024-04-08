package kinoap.app.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "reservation_tickets", joinColumns = {@JoinColumn(name = "reservation_id")},inverseJoinColumns = {@JoinColumn(name = "ticket_id")})
    private List<Ticket> tickets;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "reservation_snacks", joinColumns = {@JoinColumn(name = "reservation_id")},inverseJoinColumns = {@JoinColumn(name = "snack_id")})
    private List<Snack> snacks;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "client_reservations", joinColumns = {@JoinColumn(name = "reservation_id")},inverseJoinColumns = {@JoinColumn(name="client_id")})
    private Client customer;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "reservation_filmshow", joinColumns = {@JoinColumn(name = "reservation_id")},inverseJoinColumns = {@JoinColumn(name = "filmshow_id")})
    private FilmShow filmShow;

    @Column(name = "date_of_booking")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfBooking;

    public void addSnacks(Snack snack){
        if(snacks == null){
            snacks = new ArrayList<>();
        }
        snacks.add(snack);
    }

    public void addTicket(Ticket ticket){
        if(tickets == null){
            tickets = new ArrayList<>();
        }
        tickets.add(ticket);
    }

}
