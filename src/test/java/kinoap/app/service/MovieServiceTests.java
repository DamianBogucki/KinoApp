package kinoap.app.service;

import kinoap.app.Entity.Movie;
import kinoap.app.ServicesImpl.MovieServiceImpl;
import kinoap.app.daoImpl.MovieDaoImpl;
import kinoap.app.dtoObjects.RequestDto.MovieRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTests {
    @Mock
    private MovieDaoImpl movieDao;
    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    public void MovieService_SaveMovie_ReturnVoid() {
        MovieRequestDto movieRequestDto = new MovieRequestDto();
        movieRequestDto.setAvgRating(10);
        movieRequestDto.setDuration(123);
        movieRequestDto.setTitle("Hobbit");
        doNothing().when(movieDao).save(Mockito.any(Movie.class));
        assertAll(() -> movieService.saveMovie(movieRequestDto));
    }

    @Test
    public void MovieService_DeleteMovie_ReturnVoid() {
        int movieId = 1;
        doNothing().when(movieDao).delete(movieId);
        assertAll(() -> movieService.deleteMovie(movieId));
    }

    @Test
    public void MovieService_UpdateAllFieldsOfMovie_ReturnMovie() {
        int movieId = 1;
        MovieRequestDto movieRequestDto = new MovieRequestDto();
        movieRequestDto.setAvgRating(10);
        movieRequestDto.setDuration(123);
        movieRequestDto.setTitle("Hobbit");

        Movie movie = new Movie("Hobbit",123,10);

        when(movieDao.findMovieById(movieId)).thenReturn(movie);
        doNothing().when(movieDao).update(Mockito.any(Movie.class));

        Movie movieUpdated = movieService.updateMovie(movieId,movieRequestDto);
        assertThat(movieUpdated).isNotNull();
        assertThat(movieUpdated.getDuration()).isEqualTo(movieRequestDto.getDuration());
    }

    @Test
    public void MovieService_UpdateSomeFieldsOfMovie_ReturnMovie() {
        int movieId = 1;
        HashMap<String,Object> variables = new HashMap<>();
        variables.put("title","Hobbitt");

        Movie movie = new Movie("Hobbit",123,10);

        when(movieDao.findMovieById(movieId)).thenReturn(movie);
        Movie movieUpdated = movieService.updateMovie(movieId,variables);

        assertThat(movieUpdated).isNotNull();
        assertThat(movieUpdated.getTitle()).isEqualTo(variables.get("title"));
    }

    @Test
    public void MovieService_FindAllMovies_ReturnMovies() {
        List<Movie> movies = Mockito.mock(List.class);
        when(movieDao.findAllMovies()).thenReturn(movies);

        List<Movie> moviesGet = movieService.findAllMovies();
        assertThat(moviesGet).isNotNull();
    }

    @Test
    public void MovieService_findMoviesPageByTitle_ReturnPageOfMovies() {
        String title = "Hobbit";
        int page = 0;
        int size = 10;
        Page<Movie> pageOfMovies = Mockito.mock(Page.class);
        when(movieDao.findMoviesPageByTitle(Mockito.any(String.class),Mockito.any(Pageable.class))).thenReturn(pageOfMovies);
        Page<Movie> moviesPageGet = movieService.findMoviesPageByTitle(title, PageRequest.of(page,size));
        assertThat(moviesPageGet.getContent()).isNotNull();
    }

    @Test
    public void MovieService_findMoviesPage_ReturnMovies() {
        int page = 0;
        int size = 10;
        Page<Movie> pageOfMovies = Mockito.mock(Page.class);
        when(movieDao.findMoviesPage(Mockito.any(Pageable.class))).thenReturn(pageOfMovies);
        Page<Movie> moviesPageGet = movieService.findMoviesPage(PageRequest.of(page,size));
        assertThat(moviesPageGet.getContent()).isNotNull();
    }

    @Test
    public void MovieService_findMovieById_ReturnOptMovie(){
        int movieId = 1;
        when(movieDao.findMovieById(movieId)).thenReturn(new Movie());
        Movie movieOpt = movieService.findMovieById(movieId).get();
        assertThat(movieOpt).isNotNull();
    }

    @Test
    public void MovieService_findMovieByTitle_ReturnOptMovie(){
        String movieTitle = "Hobbit";
        when(movieDao.findMovieByTitle(movieTitle)).thenReturn(Optional.of(new Movie()));
        Movie movieOpt = movieService.findMovieByTitle(movieTitle).get();
        assertThat(movieOpt).isNotNull();
    }
}