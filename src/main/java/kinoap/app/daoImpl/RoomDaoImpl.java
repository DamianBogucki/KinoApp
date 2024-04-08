package kinoap.app.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import kinoap.app.Entity.Reservation;
import kinoap.app.Entity.Room;
import kinoap.app.dao.RoomDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoomDaoImpl implements RoomDao {
    private EntityManager entityManager;

    public RoomDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Room room) {
        entityManager.persist(room);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Room room = entityManager.find(Room.class,id);
        entityManager.remove(room);
    }

    @Override
    @Transactional
    public void update(Room room) {
        entityManager.merge(room);
    }

    @Override
    public List<Room> findAllRooms() {
        TypedQuery<Room> movies = entityManager.createQuery("from Room", Room.class);
        return movies.getResultList();
    }

    @Override
    public Room findRoomById(long id) {
        return entityManager.find(Room.class,id);
    }

    @Override
    public Optional<Room> findRoomByRoomNumber(String number) {
        TypedQuery<Room> room = entityManager.createQuery("from Room where roomNumber = :number",Room.class).setParameter("number",number);
        return room.getResultList().stream().findFirst();
    }
}
