package kinoap.app.controllers;


import jakarta.validation.Valid;
import kinoap.app.Entity.Movie;
import kinoap.app.Entity.Room;
import kinoap.app.Exceptions.MovieAlreadyExistsException;
import kinoap.app.Exceptions.MovieNotFoundException;
import kinoap.app.Exceptions.RoomAlreadyExistsException;
import kinoap.app.Exceptions.RoomNotFoundException;
import kinoap.app.dtoObjects.Mapper;
import kinoap.app.dtoObjects.RequestDto.MovieRequestDto;
import kinoap.app.dtoObjects.RequestDto.RoomRequestDto;
import kinoap.app.dtoObjects.ResponseDto.MovieResponseDto;
import kinoap.app.dtoObjects.ResponseDto.RoomResponseDto;
import kinoap.app.dtoObjects.ResponseDto.TicketResponseDto;
import kinoap.app.services.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private RoomService roomService;
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<RoomResponseDto>> getAllRooms(){
        List<RoomResponseDto> allRooms = roomService.findAllRooms().stream().map((Mapper::roomToResponseDto)).toList();
        return new ResponseEntity<>(allRooms, HttpStatus.OK);
    }

    @GetMapping("/getRoom/{roomId}")
    public ResponseEntity<RoomResponseDto> getRoomById(@PathVariable long roomId) throws RoomNotFoundException {
        Optional<Room> roomOpt = roomService.findRoomById(roomId);
        if (roomOpt.isEmpty()){
            throw new RoomNotFoundException("There is no room with this id " + roomId);
        }
        RoomResponseDto roomResponseDto = Mapper.roomToResponseDto(roomOpt.get());
        return new ResponseEntity<>(roomResponseDto,HttpStatus.OK);
    }

    @PostMapping("/addRoom")
    public ResponseEntity<?> addNewRoom(@RequestBody @Valid RoomRequestDto roomRequestDto){
        Optional<Room> roomOpt = roomService.findRoomByNumber(roomRequestDto.getRoomNumber());
        if (roomOpt.isPresent()){
            throw new RoomAlreadyExistsException("Romm with number " + roomRequestDto.getRoomNumber() + " already exists");
        }
        roomService.saveRoom(roomRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/editRoom/{roomId}")
    public ResponseEntity<RoomResponseDto> editRoom(@PathVariable long roomId, @RequestBody @Valid RoomRequestDto roomRequestDto){
        Optional<Room> roomOpt = roomService.findRoomById(roomId);
        if (roomOpt.isEmpty()){
            throw new RoomNotFoundException("There is no room with id of " + roomId);
        }
        RoomResponseDto roomResponseDto = Mapper.roomToResponseDto(roomService.updateRoom(roomId,roomRequestDto));
        return new ResponseEntity<>(roomResponseDto,HttpStatus.OK);
    }

    @PatchMapping("/editRoom/{roomId}")
    public ResponseEntity<RoomResponseDto> editRoomVariables(@PathVariable long roomId, @RequestBody Map<String,Object> variablesToUpdate){
        Optional<Room> roomOpt = roomService.findRoomById(roomId);
        if (roomOpt.isEmpty()){
            throw new RoomNotFoundException("There is no room with id of " + roomId);
        }
        RoomResponseDto roomResponseDto = Mapper.roomToResponseDto(roomService.updateRoom(roomId,variablesToUpdate));
        return new ResponseEntity<>(roomResponseDto,HttpStatus.OK);
    }

    @DeleteMapping("/deleteRoom/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable long roomId){
        Optional<Room> roomOpt = roomService.findRoomById(roomId);
        if (roomOpt.isEmpty()){
            throw new RoomNotFoundException("There is no room with id of " + roomId);
        }
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
