package kinoap.app.dao;

import kinoap.app.Entity.FilmShow;
import kinoap.app.Entity.Movie;
import kinoap.app.dtoObjects.RequestDto.MovieRequestDto;
import kinoap.app.dtoObjects.ResponseDto.MovieResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MovieDao {
    public void save(Movie movie);
    public void delete(long id);
    public void update(Movie movie);
    public List<Movie> findAllMovies();
    public Page<Movie> findMoviesPage(Pageable pageable);
    public Page<Movie> findMoviesPageByTitle(String title, Pageable pageable);
    public Movie findMovieById(long id);
    public Optional<Movie> findMovieByTitle(String title);
}
