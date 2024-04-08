package kinoap.app.ExceptionControllers;

import kinoap.app.ExceptionResponses.MovieErrorResponse;
import kinoap.app.ExceptionResponses.SnackErrorResponse;
import kinoap.app.Exceptions.MovieAlreadyExistsException;
import kinoap.app.Exceptions.MovieNotFoundException;
import kinoap.app.Exceptions.SnackAlreadyExistsException;
import kinoap.app.Exceptions.SnackNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class SnackExceptionContoller {
    @ExceptionHandler
    public ResponseEntity<SnackErrorResponse> handleSnackException(SnackNotFoundException ex){
        SnackErrorResponse snackErrorResponse = new SnackErrorResponse();
        snackErrorResponse.setMessage(ex.getMessage());
        snackErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        snackErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(snackErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<SnackErrorResponse> handleSnackAlreadyExist(SnackAlreadyExistsException ex){
        SnackErrorResponse snackErrorResponse = new SnackErrorResponse();
        snackErrorResponse.setMessage(ex.getMessage());
        snackErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        snackErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(snackErrorResponse,HttpStatus.BAD_REQUEST);
    }

}
