package kinoap.app.dao;

import kinoap.app.Entity.Room;
import kinoap.app.Entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketDao {
    public void save(Ticket ticket);
    public void delete(long id);
    public void update(Ticket ticket);
    public List<Ticket> findAllTickets();
    public Ticket findTicketById(long id);
    public Optional<Ticket> findTicketByType(String type);
}
