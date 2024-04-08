package kinoap.app.dao;

import kinoap.app.Entity.FilmShow;
import kinoap.app.Entity.Movie;
import kinoap.app.Entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationDao {
    public void save(Reservation reservation);
    public void delete(long id);
    public void update(Reservation reservation);
    public List<Reservation> findAllClientReservations(String email);
    public Reservation findReservationById(long id);

}
