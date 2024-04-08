package kinoap.app.ServicesImpl;

import jakarta.transaction.Transactional;
import kinoap.app.Entity.*;
import kinoap.app.LoggedInUser;
import kinoap.app.dao.ReservationDao;
import kinoap.app.dtoObjects.RequestDto.ReservationRequstDto;
import kinoap.app.services.FilmShowService;
import kinoap.app.services.ReservationService;
import kinoap.app.services.SnackService;
import kinoap.app.services.TicketService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationDao reservationDao;
    private FilmShowService filmShowService;
    private SnackService snackService;
    private TicketService ticketService;

    public ReservationServiceImpl(ReservationDao reservationDao, FilmShowService filmShowService, SnackService snackService, TicketService ticketService) {
        this.reservationDao = reservationDao;
        this.filmShowService = filmShowService;
        this.snackService = snackService;
        this.ticketService = ticketService;
    }

    @Override
    public List<Reservation> findAllClientReservations(){
        return reservationDao.findAllClientReservations(LoggedInUser.getLoggedUnUser().getEmail());
    }

    @Override
    @Transactional
    public void saveReservation(ReservationRequstDto reservationRequstDto) {
        Reservation reservation = new Reservation();
        reservation.setDateOfBooking(LocalDateTime.now());
        reservation.setFilmShow(filmShowService.findFilmShowById(reservationRequstDto.getFilmShowId()).get());

        List<Snack> snacks = new ArrayList<>();
        for (Integer i : reservationRequstDto.getSnackIds()){
            snacks.add(snackService.findSnackById(i).get());
        }

        reservation.setSnacks(snacks);

        List<Ticket> tickets = new ArrayList<>();
        for (Integer i : reservationRequstDto.getTicketIds()){
            tickets.add(ticketService.findTicketById(i).get());
        }

        reservation.setTickets(tickets);

        reservation.setCustomer(LoggedInUser.getLoggedUnUser());

        reservationDao.save(reservation);

    }

    @Override
    @Transactional
    public void deleteReservation(long id) {
        reservationDao.delete(id);
    }

    @Override
    public Optional<Reservation> findReservationById(long id) {
        return Optional.ofNullable(reservationDao.findReservationById(id));
    }

    @Override
    @Transactional
    public Reservation updateReservation(long id, ReservationRequstDto reservationRequstDto) {
        Reservation reservation = reservationDao.findReservationById(id);
        List<Snack> snacks = new ArrayList<>();
        for (Integer snackId : reservationRequstDto.getSnackIds()){
            snacks.add(snackService.findSnackById(snackId).get());
        }
        reservation.setSnacks(snacks);

        List<Ticket> tickets = new ArrayList<>();
        for (Integer ticketId : reservationRequstDto.getTicketIds()){
            tickets.add(ticketService.findTicketById(ticketId).get());
        }
        reservation.setTickets(tickets);

        reservation.setFilmShow(filmShowService.findFilmShowById(reservationRequstDto.getFilmShowId()).get());
        return reservation;
    }
}
