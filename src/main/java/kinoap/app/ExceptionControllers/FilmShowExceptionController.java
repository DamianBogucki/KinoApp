package kinoap.app.ExceptionControllers;

import kinoap.app.Entity.FilmShow;
import kinoap.app.ExceptionResponses.FilmShowErrorResponse;
import kinoap.app.Exceptions.FilmShowAlreadyExistsEx;
import kinoap.app.Exceptions.FilmShowNotExistsEx;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FilmShowExceptionController {
    @ExceptionHandler
    public ResponseEntity<FilmShowErrorResponse> handleFilmShowException(FilmShowAlreadyExistsEx ex){
        FilmShowErrorResponse filmShowErrorResponse = new FilmShowErrorResponse();
        filmShowErrorResponse.setMessage(ex.getMessage());
        filmShowErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        filmShowErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(filmShowErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<FilmShowErrorResponse> handleFilmShowException(FilmShowNotExistsEx ex){
        FilmShowErrorResponse filmShowErrorResponse = new FilmShowErrorResponse();
        filmShowErrorResponse.setMessage(ex.getMessage());
        filmShowErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        filmShowErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(filmShowErrorResponse,HttpStatus.BAD_REQUEST);
    }
}
