package kinoap.app;

import kinoap.app.dao.MovieDao;
import kinoap.app.dtoObjects.RequestDto.FilmShowRequestDto;
import kinoap.app.dtoObjects.RequestDto.MovieRequestDto;
import kinoap.app.services.FilmShowService;
import kinoap.app.services.MovieService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}



}
