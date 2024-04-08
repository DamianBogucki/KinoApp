package kinoap.app.ServicesImpl;

import jakarta.transaction.Transactional;
import kinoap.app.Entity.Movie;
import kinoap.app.Exceptions.MovieAlreadyExistsException;
import kinoap.app.Exceptions.MovieNotFoundException;
import kinoap.app.dao.MovieDao;
import kinoap.app.dtoObjects.Mapper;
import kinoap.app.dtoObjects.RequestDto.MovieRequestDto;
import kinoap.app.dtoObjects.ResponseDto.MovieResponseDto;
import kinoap.app.services.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieDao movieDao;

    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    @Transactional
    public void saveMovie(MovieRequestDto movieRequestDto) {
        Movie movie = new Movie();
        movie.setTitle(movieRequestDto.getTitle());
        movie.setDuration(movieRequestDto.getDuration());
        movie.setAvgRating(movieRequestDto.getAvgRating());
        movieDao.save(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(long id) {
        movieDao.delete(id);
    }

    @Override
    @Transactional
    public Movie updateMovie(long movieId, MovieRequestDto movieRequestDto) {
        Movie movie = movieDao.findMovieById(movieId);
        movie.setAvgRating(movieRequestDto.getAvgRating());
        movie.setTitle(movieRequestDto.getTitle());
        movie.setDuration(movieRequestDto.getDuration());
        movieDao.update(movie);
        return movie;
    }

    @Override
    @Transactional
    public Movie updateMovie(long id, Map<String, Object> variablesToUpdate) {
        Movie movie = movieDao.findMovieById(id);
        variablesToUpdate.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(Movie.class,(String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,movie,value);
        });
        return movie;
    }

    @Override
    public List<Movie> findAllMovies() {
        return movieDao.findAllMovies();
    }

    @Override
    public Optional<Movie> findMovieById(long id) {
        Optional<Movie> movie = Optional.ofNullable(movieDao.findMovieById(id));
        return movie;
    }

    @Override
    public Optional<Movie> findMovieByTitle(String title) {
        return movieDao.findMovieByTitle(title);
    }

    @Override
    public Page<Movie> findMoviesPageByTitle(String title, Pageable pageable) {
        return movieDao.findMoviesPageByTitle(title, pageable);
    }

    @Override
    public Page<Movie> findMoviesPage(Pageable pageable) {
        return movieDao.findMoviesPage(pageable);
    }
}
