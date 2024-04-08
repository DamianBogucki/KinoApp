package kinoap.app.services;

import kinoap.app.Entity.FilmShow;
import kinoap.app.Entity.Movie;
import kinoap.app.dao.FilmShowDao;
import kinoap.app.dtoObjects.RequestDto.FilmShowRequestDto;
import kinoap.app.dtoObjects.RequestDto.MovieRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FilmShowService {
    public void deleteFilmShow(long id);
    public void saveFilmShow(FilmShowRequestDto filmShowRequestDto);
    public List<FilmShow> findAllFilmShows();
    public Page<FilmShow> findFilmShowsPage(Pageable pageable);
    public Page<FilmShow> findFilmShowsPageByTitle(String title, Pageable pageable);
    public Optional<FilmShow> findFilmShowById(long id);
    public FilmShow updateFilmShow(long id, FilmShowRequestDto filmShowRequestDto);
    public Optional<FilmShow> findFilmShow(FilmShowRequestDto filmShowRequestDto);
}
