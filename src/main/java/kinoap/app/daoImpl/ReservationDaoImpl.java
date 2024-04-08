package kinoap.app.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import kinoap.app.Entity.Movie;
import kinoap.app.Entity.Reservation;
import kinoap.app.dao.ReservationDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationDaoImpl implements ReservationDao {
    private EntityManager entityManager;

    public ReservationDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Reservation reservation) {
        entityManager.persist(reservation);
    }

    @Override
    public void delete(long id) {
        Reservation reservation = entityManager.find(Reservation.class,id);
        entityManager.remove(reservation);
    }

    @Override
    public void update(Reservation reservation) {
        this.entityManager.merge(reservation);
    }

    @Override
    public List<Reservation> findAllClientReservations(String email) {
        TypedQuery<Reservation> movies = entityManager.createQuery(
                "select r from Reservation r left join r.customer c where c.email = :email", Reservation.class)
                .setParameter("email",email);
        return movies.getResultList();
    }

    @Override
    public Reservation findReservationById(long id) {
        return entityManager.find(Reservation.class,id);
    }





}


