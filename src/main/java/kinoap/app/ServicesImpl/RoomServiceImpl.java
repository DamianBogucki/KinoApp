package kinoap.app.ServicesImpl;

import jakarta.transaction.Transactional;
import kinoap.app.Entity.Movie;
import kinoap.app.Entity.Room;
import kinoap.app.dao.RoomDao;
import kinoap.app.dtoObjects.RequestDto.RoomRequestDto;
import kinoap.app.services.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.ref.PhantomReference;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService{
    private RoomDao roomDao;
    public RoomServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }
    @Override
    @Transactional
    public void saveRoom(RoomRequestDto roomRequestDto) {
        Room room = new Room();
        room.setRoomNumber(roomRequestDto.getRoomNumber());
        room.setRoomType(roomRequestDto.getRoomType());
        room.setNumberOfSeats(roomRequestDto.getNumberOfSeats());
        roomDao.save(room);
    }

    @Override
    @Transactional
    public void deleteRoom(long id) {
        roomDao.delete(id);
    }

    @Override
    @Transactional
    public Room updateRoom(long id, RoomRequestDto roomRequestDto) {
        Room room = roomDao.findRoomById(id);
        room.setRoomNumber(roomRequestDto.getRoomNumber());
        room.setRoomType(roomRequestDto.getRoomType());
        room.setNumberOfSeats(roomRequestDto.getNumberOfSeats());
        roomDao.update(room);
        return room;
    }

    @Override
    @Transactional
    public Room updateRoom(long id, Map<String, Object> roomEditVariables) {
        Room room = roomDao.findRoomById(id);
        roomEditVariables.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(Room.class,(String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,room,value);
        });
        return room;
    }

    @Override
    public List<Room> findAllRooms() {
        return roomDao.findAllRooms();
    }

    @Override
    public Optional<Room> findRoomById(long id) {
        Optional<Room> room = Optional.ofNullable(roomDao.findRoomById(id));
        return room;
    }

    @Override
    public Optional<Room> findRoomByNumber(String number) {
        return roomDao.findRoomByRoomNumber(number);
    }
}
