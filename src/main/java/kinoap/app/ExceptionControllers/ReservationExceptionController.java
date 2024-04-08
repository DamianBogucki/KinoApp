package kinoap.app.ExceptionControllers;

import kinoap.app.ExceptionResponses.MovieErrorResponse;
import kinoap.app.ExceptionResponses.ReservationErrorResponse;
import kinoap.app.Exceptions.MovieNotFoundException;
import kinoap.app.Exceptions.ReservationNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservationExceptionController {
    @ExceptionHandler(ReservationNotExistsException.class)
    public ResponseEntity<ReservationErrorResponse> handleReservationException(ReservationNotExistsException ex){
        ReservationErrorResponse reservationErrorResponse = new ReservationErrorResponse();
        reservationErrorResponse.setMessage(ex.getMessage());
        reservationErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        reservationErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(reservationErrorResponse,HttpStatus.BAD_REQUEST);
    }

}
