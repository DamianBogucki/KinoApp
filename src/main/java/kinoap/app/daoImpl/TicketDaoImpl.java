package kinoap.app.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import kinoap.app.Entity.Movie;
import kinoap.app.Entity.Reservation;
import kinoap.app.Entity.Snack;
import kinoap.app.Entity.Ticket;
import kinoap.app.dao.TicketDao;
import org.apache.commons.lang3.reflect.Typed;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TicketDaoImpl implements TicketDao {
    private EntityManager entityManager;

    public TicketDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Ticket ticket) {
        entityManager.persist(ticket);
    }

    @Override
    public void delete(long id) {
        Ticket ticket = entityManager.find(Ticket.class,id);
        entityManager.remove(ticket);
    }

    @Override
    public void update(Ticket ticket) {
        this.entityManager.merge(ticket);
    }

    @Override
    public List<Ticket> findAllTickets() {
        TypedQuery<Ticket> movies = entityManager.createQuery("from Ticket", Ticket.class);
        return movies.getResultList();
    }

    @Override
    public Ticket findTicketById(long id) {
        return entityManager.find(Ticket.class,id);
    }

    @Override
    public Optional<Ticket> findTicketByType(String type) {
        TypedQuery<Ticket> ticketOpt = entityManager.createQuery("from Ticket where type=:type",Ticket.class).setParameter("type",type);
        return ticketOpt.getResultList().stream().findFirst();
    }



}
