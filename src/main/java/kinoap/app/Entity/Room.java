package kinoap.app.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "room_number")
    private String roomNumber;
    @Column(name = "room_type")
    private String roomType;
    @Column(name = "number_of_seats")
    private long numberOfSeats;

    public Room(String roomNumber, String roomType, long numberOfSeats) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.numberOfSeats = numberOfSeats;
    }
}
