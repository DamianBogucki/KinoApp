package kinoap.app.ServicesImpl;

import jakarta.transaction.Transactional;
import kinoap.app.Entity.Movie;
import kinoap.app.Entity.Ticket;
import kinoap.app.Exceptions.TicketAlreadyExistsException;
import kinoap.app.Exceptions.TicketNotFoundException;
import kinoap.app.dao.TicketDao;
import kinoap.app.dtoObjects.Mapper;
import kinoap.app.dtoObjects.RequestDto.TicketRequestDto;
import kinoap.app.dtoObjects.ResponseDto.MovieResponseDto;
import kinoap.app.dtoObjects.ResponseDto.TicketResponseDto;
import kinoap.app.services.TicketService;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private TicketDao ticketDao;

    public TicketServiceImpl(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    @Transactional
    public void saveTicket(TicketRequestDto ticketRequestDto) {
        Ticket ticket = new Ticket();
        ticket.setType(ticketRequestDto.getType());
        ticket.setPrice(ticketRequestDto.getPrice());
        ticketDao.save(ticket);
    }

    @Override
    @Transactional
    public void deleteTicket(long id) {
        ticketDao.delete(id);
    }

    @Override
    @Transactional
    public Ticket updateTicket(long id, TicketRequestDto ticketRequestDto) {
        Ticket ticket = ticketDao.findTicketById(id);
        ticket.setPrice(ticketRequestDto.getPrice());
        ticket.setType(ticketRequestDto.getType());
        ticketDao.update(ticket);
        return ticket;
    }

    @Override
    @Transactional
    public Ticket updateTicket(long id, Map<String, Object> variablesToUpdate) {
        Ticket ticket = ticketDao.findTicketById(id);
        variablesToUpdate.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(Ticket.class,(String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,ticket,value);
        });
        return ticket;
    }

    @Override
    public List<Ticket> findAllTickets() {
        return ticketDao.findAllTickets();
    }

    @Override
    public Optional<Ticket> findTicketById(long id) {
        Optional<Ticket> ticket = Optional.ofNullable(ticketDao.findTicketById(id));
        return ticket;
    }

    @Override
    public Optional<Ticket> findTicketByType(String type) {
        return ticketDao.findTicketByType((type));
    }
}
