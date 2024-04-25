package kinoap.app.service;

import kinoap.app.Entity.*;
import kinoap.app.ServicesImpl.FilmShowServiceImpl;
import kinoap.app.ServicesImpl.ReservationServiceImpl;
import kinoap.app.ServicesImpl.SnackServiceImpl;
import kinoap.app.ServicesImpl.TicketServiceImpl;
import kinoap.app.daoImpl.ReservationDaoImpl;
import kinoap.app.dtoObjects.RequestDto.ReservationRequstDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {
    @Mock
    private ReservationDaoImpl reservationDao;
    @Mock
    private FilmShowServiceImpl filmShowService;
    @Mock
    private SnackServiceImpl snackService;
    @Mock
    private TicketServiceImpl ticketService;
    @InjectMocks
    private ReservationServiceImpl reservationService;
    @Test
    public void ReservationService_SaveReservation_ReturnVoid(){
        UserDetails userDetails = new Client();
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        ReservationRequstDto reservationRequstDto = new ReservationRequstDto();
        reservationRequstDto.setFilmShowId(1);
        reservationRequstDto.setSnackIds(new ArrayList<>(List.of(1)));
        reservationRequstDto.setTicketIds(new ArrayList<>(List.of(1)));
        
        when(snackService.findSnackById(reservationRequstDto.getSnackIds().get(0))).thenReturn(Optional.of(new Snack()));
        when(ticketService.findTicketById(reservationRequstDto.getTicketIds().get(0))).thenReturn(Optional.of(new Ticket()));
        when(filmShowService.findFilmShowById(reservationRequstDto.getFilmShowId())).thenReturn(Optional.of(new FilmShow()));
        doNothing().when(reservationDao).save(Mockito.any(Reservation.class));

        assertAll(() -> reservationService.saveReservation(reservationRequstDto));
    }

    @Test
    public void ReservationService_DeleteReservation_ReturnVoid(){
        int reservationId = 1;
        doNothing().when(reservationDao).delete(reservationId);
        assertAll(() -> reservationService.deleteReservation(reservationId));
    }

    @Test
    public void ReservationService_FindReservationById_ReturnVoid() {
        int reservationId = 1;
        Reservation reservation = new Reservation();
        when(reservationDao.findReservationById(reservationId)).thenReturn(reservation);
        Reservation reservationOpt = reservationService.findReservationById(reservationId).get();
        assertThat(reservationOpt).isNotNull();
    }
    @Test
    public void ReservationService_UpdateReservation_ReturnReservation() {
        int reservationId = 1;
        ReservationRequstDto reservationRequstDto = new ReservationRequstDto();
        reservationRequstDto.setFilmShowId(1);
        reservationRequstDto.setSnackIds(new ArrayList<>(List.of(1)));
        reservationRequstDto.setTicketIds(new ArrayList<>(List.of(1)));

        Reservation reservation = new Reservation();
        Snack snack = new Snack("Popcorn","salt",100);
        Ticket ticket = new Ticket("normal", 10);
        FilmShow filmShow = new FilmShow(LocalDateTime.of(2024, 5, 14, 13, 30));

        when(reservationDao.findReservationById(reservationId)).thenReturn(reservation);
        when(snackService.findSnackById(reservationRequstDto.getSnackIds().get(0))).thenReturn(Optional.of(snack));
        when(ticketService.findTicketById(reservationRequstDto.getTicketIds().get(0))).thenReturn(Optional.of(ticket));
        when(filmShowService.findFilmShowById(reservationRequstDto.getFilmShowId())).thenReturn(Optional.of(filmShow));

        Reservation reservationUpdated = reservationService.updateReservation(reservationId,reservationRequstDto);
        assertThat(reservationUpdated).isNotNull();
    }
}