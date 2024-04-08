package kinoap.app.services;

import kinoap.app.Entity.Movie;
import kinoap.app.Entity.Ticket;
import kinoap.app.dtoObjects.RequestDto.MovieRequestDto;
import kinoap.app.dtoObjects.RequestDto.TicketRequestDto;
import kinoap.app.dtoObjects.ResponseDto.MovieResponseDto;
import kinoap.app.dtoObjects.ResponseDto.TicketResponseDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TicketService {
    public void saveTicket(TicketRequestDto ticketRequestDto);
    public void deleteTicket(long id);
    public Ticket updateTicket(long id,TicketRequestDto ticketResponseDto);
    public Ticket updateTicket(long id,Map<String,Object> ticketRequestDto);
    public List<Ticket> findAllTickets();
    public Optional<Ticket> findTicketById(long id);
    public Optional<Ticket> findTicketByType(String type);
}
