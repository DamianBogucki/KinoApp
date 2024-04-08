package kinoap.app.daoImpl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import kinoap.app.Entity.Reservation;
import kinoap.app.Entity.Room;
import kinoap.app.Entity.Snack;
import kinoap.app.dao.SnackDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SnackDaoImpl implements SnackDao {
    private EntityManager entityManager;

    public SnackDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Snack snack) {
        entityManager.persist(snack);
    }

    @Override
    public void delete(long id) {
        Snack snack = entityManager.find(Snack.class,id);
        entityManager.remove(snack);
    }

    @Override
    public void update(Snack snack) {
        this.entityManager.merge(snack);
    }

    @Override
    public List<Snack> findAllSnacks() {
        TypedQuery<Snack> movies = entityManager.createQuery("from Snack", Snack.class);
        return movies.getResultList();
    }

    @Override
    public Snack findSnackById(long id) {
        return entityManager.find(Snack.class,id);
    }

    @Override
    public Optional<Snack> findSnackByName(String name) {
        TypedQuery<Snack> snack = entityManager.createQuery("from Snack where name = :name",Snack.class).setParameter("name",name);
        return snack.getResultList().stream().findFirst();
    }
}
