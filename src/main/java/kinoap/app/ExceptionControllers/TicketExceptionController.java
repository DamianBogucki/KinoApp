package kinoap.app.ExceptionControllers;

import kinoap.app.ExceptionResponses.TicketErrorResponse;
import kinoap.app.Exceptions.TicketAlreadyExistsException;
import kinoap.app.Exceptions.TicketNotFoundException;
import kinoap.app.dtoObjects.ResponseDto.TicketResponseDto;
import lombok.Data;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;

@ControllerAdvice
public class TicketExceptionController {
    @ExceptionHandler
    public ResponseEntity<TicketErrorResponse> handleTicketAlreadyExists(TicketAlreadyExistsException ex){
        TicketErrorResponse ticketErrorResponse = new TicketErrorResponse();
        ticketErrorResponse.setMessage(ex.getMessage());
        ticketErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        ticketErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(ticketErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<TicketErrorResponse> handleTicketNotExists(TicketNotFoundException ex){
        TicketErrorResponse ticketErrorResponse = new TicketErrorResponse();
        ticketErrorResponse.setMessage(ex.getMessage());
        ticketErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        ticketErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(ticketErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
