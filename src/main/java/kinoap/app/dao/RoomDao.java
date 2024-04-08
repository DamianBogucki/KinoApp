package kinoap.app.dao;

import kinoap.app.Entity.Movie;
import kinoap.app.Entity.Room;

import java.util.List;
import java.util.Optional;

public interface RoomDao {
    public void save(Room room);
    public void delete(long id);
    public void update(Room room);
    public List<Room> findAllRooms();
    public Room findRoomById(long id);
    public Optional<Room> findRoomByRoomNumber(String number);
}
