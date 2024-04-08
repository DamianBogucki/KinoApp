package kinoap.app.ExceptionControllers;

import kinoap.app.ExceptionResponses.RoomErrorResponse;
import kinoap.app.ExceptionResponses.SnackErrorResponse;
import kinoap.app.Exceptions.RoomAlreadyExistsException;
import kinoap.app.Exceptions.RoomNotFoundException;
import kinoap.app.Exceptions.SnackAlreadyExistsException;
import kinoap.app.Exceptions.SnackNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class RoomExceptionController {
    @ExceptionHandler
    public ResponseEntity<RoomErrorResponse> handleRoomException(RoomNotFoundException ex){
        RoomErrorResponse roomErrorResponse = new RoomErrorResponse();
        roomErrorResponse.setMessage(ex.getMessage());
        roomErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        roomErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(roomErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<RoomErrorResponse> handleRoomAlreadyExist(RoomAlreadyExistsException ex){
        RoomErrorResponse roomErrorResponse = new RoomErrorResponse();
        roomErrorResponse.setMessage(ex.getMessage());
        roomErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        roomErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(roomErrorResponse,HttpStatus.BAD_REQUEST);
    }
}