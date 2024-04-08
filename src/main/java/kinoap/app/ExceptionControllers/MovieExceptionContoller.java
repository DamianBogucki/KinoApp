package kinoap.app.ExceptionControllers;

import jakarta.persistence.NamedNativeQueries;
import kinoap.app.ExceptionResponses.MovieErrorResponse;
import kinoap.app.Exceptions.MovieAlreadyExistsException;
import kinoap.app.Exceptions.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class MovieExceptionContoller {

    @ExceptionHandler
    public ResponseEntity<MovieErrorResponse> handleMovieException(MovieNotFoundException mnfe){
        MovieErrorResponse movieErrorResponse = new MovieErrorResponse();
        movieErrorResponse.setMessage(mnfe.getMessage());
        movieErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        movieErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(movieErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<MovieErrorResponse> handleMovieAlreadyExist(MovieAlreadyExistsException ex){
        MovieErrorResponse movieErrorResponse = new MovieErrorResponse();
        movieErrorResponse.setMessage(ex.getMessage());
        movieErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        movieErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(movieErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValidMovieInput(MethodArgumentNotValidException ex){
        Map<String,String> errorMap = ex.getAllErrors()
                .stream()
                .collect(Collectors.toMap(x -> ((FieldError)x).getField(),
                        b -> b.getDefaultMessage(),(p,q) -> p, LinkedHashMap::new));
        errorMap.put("timestamp",(new Date()).toString());
        return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
    }

}


