package kinoap.app.services;

import kinoap.app.Entity.Movie;
import kinoap.app.dtoObjects.RequestDto.MovieRequestDto;
import kinoap.app.dtoObjects.ResponseDto.MovieResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MovieService {
    public void saveMovie(MovieRequestDto movieRequestDto);
    public void deleteMovie(long id);
    public Movie updateMovie(long id, MovieRequestDto movieRequestDto);
    public Movie updateMovie(long id, Map<String,Object> variablesToUpdate);
    public List<Movie> findAllMovies();
    public Page<Movie> findMoviesPageByTitle(String title, Pageable pageable);
    public Page<Movie> findMoviesPage(Pageable pageable);
    public Optional<Movie> findMovieById(long id);
    public Optional<Movie> findMovieByTitle(String title);
}
