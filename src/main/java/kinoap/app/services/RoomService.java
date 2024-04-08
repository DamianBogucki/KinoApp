package kinoap.app.services;

import kinoap.app.Entity.Room;
import kinoap.app.Entity.Snack;
import kinoap.app.dtoObjects.RequestDto.RoomRequestDto;
import kinoap.app.dtoObjects.RequestDto.SnackRequestDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoomService {
    public void saveRoom(RoomRequestDto roomRequestDto);
    public void deleteRoom(long id);
    public Room updateRoom(long id, RoomRequestDto roomRequestDto);
    public Room updateRoom(long id, Map<String,Object> roomEditVariables);
    public List<Room> findAllRooms();
    public Optional<Room> findRoomById(long id);
    public Optional<Room> findRoomByNumber(String number);
}
