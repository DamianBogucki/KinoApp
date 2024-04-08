package kinoap.app.services;

import kinoap.app.Entity.Movie;
import kinoap.app.Entity.Reservation;
import kinoap.app.dtoObjects.RequestDto.MovieRequestDto;
import kinoap.app.dtoObjects.RequestDto.ReservationRequstDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReservationService {
    public void saveReservation(ReservationRequstDto reservationRequstDto);
    public void deleteReservation(long id);
    public Reservation updateReservation(long id, ReservationRequstDto reservationRequstDto);
    public List<Reservation> findAllClientReservations();
    public Optional<Reservation> findReservationById(long id);
}
