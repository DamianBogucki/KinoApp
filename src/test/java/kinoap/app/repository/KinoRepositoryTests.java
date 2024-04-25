package kinoap.app.repository;

import jakarta.persistence.EntityManager;
import kinoap.app.Entity.Movie;
import kinoap.app.ServicesImpl.MovieServiceImpl;
import kinoap.app.dao.MovieDao;
import kinoap.app.daoImpl.MovieDaoImpl;
import kinoap.app.dtoObjects.RequestDto.MovieRequestDto;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


//@Transactional(propagation = Propagation.NOT_SUPPORTED)
//@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@RunWith(SpringRunner.class)
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {MovieDaoImpl.class})
public class KinoRepositoryTests {
    @Autowired
    private MovieDao movieDao;

    @Test
    public void MovieRepository_SaveMovie_ReturnMovieIsNotNull() {
        Movie movie = new Movie("Hobbit", 123, 5);
        movieDao.save(movie);

        Movie movieCheck = movieDao.findMovieByTitle(movie.getTitle()).get();
        assertThat(movieCheck).isNotNull();
        Assertions.assertNotNull(movieCheck);

        assertThat(movieDao).isNotNull();
    }

    @Test
    public void MovieRepository_SaveAll_ReturnMovieIsNotHalo(){

        String cos = "siema";

        Assertions.assertEquals(cos,"siema");
    }
}


//@ExtendWith(MockitoExtension.class)
//public class KinoRepositoryTests {
//
//    @Mock
//    private MovieDaoImpl movieDao;
//    @InjectMocks
//    private MovieServiceImpl movieService;
//
//    @Test
//    public void MovieService_CreateMovie_ReturnIsNotNull(){
//        Movie movie = new Movie("Hobbit",123,7);
//        MovieRequestDto movieRequestDto = new MovieRequestDto();
//        movieRequestDto.setTitle("Harry Piter");
//        movieRequestDto.setDuration(124);
//        movieRequestDto.setAvgRating(9);
//
//        doNothing().when(movieDao).save(Mockito.any(Movie.class));
//
//        movieService.saveMovie(movieRequestDto);
//        assertThat(movie1).isNull();
//
//        assertAll(() -> movieService.saveMovie(movieRequestDto));
//
//    }
//
//}