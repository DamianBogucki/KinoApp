package kinoap.app.service;

import kinoap.app.Entity.Room;
import kinoap.app.ServicesImpl.RoomServiceImpl;
import kinoap.app.daoImpl.RoomDaoImpl;
import kinoap.app.dtoObjects.RequestDto.RoomRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTests {
    @Mock
    private RoomDaoImpl roomDao;
    @InjectMocks
    private RoomServiceImpl roomService;

    @Test
    public void RoomService_SaveRoom_ReturnVoid() {
        RoomRequestDto roomRequestDto = new RoomRequestDto();
        roomRequestDto.setRoomType("vip");
        roomRequestDto.setRoomNumber("101");
        roomRequestDto.setNumberOfSeats(100);
        doNothing().when(roomDao).save(Mockito.any(Room.class));

        assertAll(() -> roomService.saveRoom(roomRequestDto));
    }

    @Test
    public void RoomService_DeleteRoom_ReturnVoid() {
        int roomId = 1;
        doNothing().when(roomDao).delete(roomId);
        assertAll(() -> roomService.deleteRoom(roomId));
    }

    @Test
    public void RoomService_UpdateAllFieldsOfRoom_ReturnRoom() {
        int roomId = 1;
        RoomRequestDto roomRequestDto = new RoomRequestDto();
        roomRequestDto.setRoomType("vip");
        roomRequestDto.setRoomNumber("101");
        roomRequestDto.setNumberOfSeats(100);
        Room room = new Room("101","normal",200);

        when(roomDao.findRoomById(roomId)).thenReturn(room);
        doNothing().when(roomDao).update(Mockito.any(Room.class));

        Room roomUpdated = roomService.updateRoom(roomId, roomRequestDto);

        assertThat(roomUpdated).isNotNull();
        assertThat(roomUpdated.getNumberOfSeats()).isEqualTo(roomRequestDto.getNumberOfSeats());
    }

    @Test
    public void RoomService_UpdateSomeFieldsOfRoom_ReturnRoom() {
        int roomId = 1;
        HashMap<String, Object> newData = new HashMap<>();
        newData.put("roomType", "vip");

        when(roomDao.findRoomById(roomId)).thenReturn(new Room());
        Room roomUpdated = roomService.updateRoom(roomId, newData);
        assertThat(roomUpdated).isNotNull();
        assertThat(roomUpdated.getRoomType()).isEqualTo(newData.get("roomType"));
    }

    @Test
    public void RoomService_FindAllRooms_ReturnRooms() {
        List<Room> rooms = Mockito.mock(List.class);
        when(roomDao.findAllRooms()).thenReturn(rooms);

        List<Room> roomsGet = roomService.findAllRooms();
        assertThat(roomService).isNotNull();
    }

    @Test
    public void RoomService_FindRoomById_ReturnOptRoom() {
        int roomId = 1;

        when(roomDao.findRoomById(roomId)).thenReturn(new Room());
        Room roomOpt = roomService.findRoomById(roomId).get();
        assertThat(roomOpt).isNotNull();
    }

    @Test
    public void RoomService_FindRoomByNumber_ReturnRoom() {
        String roomNumber = "101";

        when(roomDao.findRoomByRoomNumber(roomNumber)).thenReturn(Optional.of(new Room()));
        Room roomOpt = roomService.findRoomByNumber(roomNumber).get();
        assertThat(roomOpt).isNotNull();
    }
}