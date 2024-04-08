package kinoap.app.dtoObjects;

import kinoap.app.Entity.*;
import kinoap.app.dtoObjects.ResponseDto.*;

import java.time.LocalDateTime;
import java.util.List;

public class Mapper {
    public static MovieResponseDto movieToResponseDto(Movie movie){
        MovieResponseDto movieResponseDto = new MovieResponseDto();
        movieResponseDto.setId(movie.getId());
        movieResponseDto.setAvgRating(movie.getAvgRating());
        movieResponseDto.setDuration(movie.getDuration());
        movieResponseDto.setTitle(movie.getTitle());
        return movieResponseDto;
    }

    public static TicketResponseDto ticketToResponseDto(Ticket ticket){
        TicketResponseDto ticketResponseDto = new TicketResponseDto();
        ticketResponseDto.setId(ticket.getId());
        ticketResponseDto.setType(ticket.getType());
        ticketResponseDto.setPrice(ticket.getPrice());
        return ticketResponseDto;
    }

    public static SnackResponseDto snackToResponseDto(Snack snack){
        SnackResponseDto snackResponseDto = new SnackResponseDto();
        snackResponseDto.setId(snack.getId());
        snackResponseDto.setName(snack.getName());
        snackResponseDto.setState(snack.getState());
        snackResponseDto.setType(snack.getType());
        return snackResponseDto;
    }

    public static RoomResponseDto roomToResponseDto(Room room){
        RoomResponseDto roomResponseDto = new RoomResponseDto();
        roomResponseDto.setId(room.getId());
        roomResponseDto.setRoomNumber(room.getRoomNumber());
        roomResponseDto.setRoomType(room.getRoomType());
        roomResponseDto.setNumberOfSeats(room.getNumberOfSeats());
        return roomResponseDto;
    }

    public static FilmShowResponseDto filmShowToResponseDto(FilmShow filmShow){
        FilmShowResponseDto filmShowResponseDto = new FilmShowResponseDto();
        filmShowResponseDto.setId(filmShow.getId());
        filmShowResponseDto.setDateOfFilmScreening(filmShow.getDateOfFilmScreening());
        filmShowResponseDto.setRoom(roomToResponseDto(filmShow.getRoom()));
        filmShowResponseDto.setMovie(movieToResponseDto(filmShow.getMovie()));
        return filmShowResponseDto;
    }

    public static ReservationResponseDto reservationToResponseDto(Reservation reservation){
        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setId(reservation.getId());
        reservationResponseDto.setDateOfBooking(reservation.getDateOfBooking());
        reservationResponseDto.setFilmShow(filmShowToResponseDto(reservation.getFilmShow()));
        reservationResponseDto.setTickets(
                reservation.getTickets().stream().map(Mapper::ticketToResponseDto).toList()
        );
        reservationResponseDto.setSnacks(
                reservation.getSnacks().stream().map(Mapper::snackToResponseDto).toList()
        );
        return reservationResponseDto;
    }

}
