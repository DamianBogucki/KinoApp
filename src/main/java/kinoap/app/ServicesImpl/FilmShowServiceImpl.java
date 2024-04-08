package kinoap.app.ServicesImpl;

import jakarta.transaction.Transactional;
import kinoap.app.Entity.FilmShow;
import kinoap.app.Entity.Movie;
import kinoap.app.Entity.Room;
import kinoap.app.Exceptions.MovieNotFoundException;
import kinoap.app.Exceptions.RoomNotFoundException;
import kinoap.app.dao.FilmShowDao;
import kinoap.app.dtoObjects.RequestDto.FilmShowRequestDto;
import kinoap.app.services.FilmShowService;
import kinoap.app.services.MovieService;
import kinoap.app.services.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FilmShowServiceImpl implements FilmShowService {
    private final FilmShowDao filmShowDao;
    private final MovieService movieService;
    private final RoomService roomService;

    public FilmShowServiceImpl(FilmShowDao filmShowDao, MovieService movieService, RoomService roomService) {
        this.filmShowDao = filmShowDao;
        this.movieService = movieService;
        this.roomService = roomService;
    }

    @Override
    @Transactional
    public void deleteFilmShow(long id) {
        filmShowDao.delete(id);
    }

    @Override
    @Transactional
    public void saveFilmShow(FilmShowRequestDto filmShowRequestDto) {
        FilmShow filmShow = new FilmShow();
        filmShow.setDateOfFilmScreening(filmShowRequestDto.getDateOfFilmScreening());
        filmShow.setRoom(roomService.findRoomById(filmShowRequestDto.getRoomId()).get());
        filmShow.setMovie(movieService.findMovieById(filmShowRequestDto.getMovieId()).get());
        filmShowDao.save(filmShow);
    }

    @Override
    public List<FilmShow> findAllFilmShows() {
        return filmShowDao.findAllFilmShows();
    }

    @Override
    public Optional<FilmShow> findFilmShowById(long id) {
        Optional<FilmShow> filmShow = Optional.ofNullable(filmShowDao.getFilmShowById(id));
        return filmShow;
    }

    @Override
    public FilmShow updateFilmShow(long id, FilmShowRequestDto filmShowRequestDto) {
        FilmShow filmShow = filmShowDao.getFilmShowById(id);
        filmShow.setDateOfFilmScreening(filmShowRequestDto.getDateOfFilmScreening());
        Optional<Room> roomOpt = roomService.findRoomById(filmShowRequestDto.getRoomId());
        if (roomOpt.isEmpty()){
            throw new RoomNotFoundException("There is no room with this id");
        }
        filmShow.setRoom(roomOpt.get());
        Optional<Movie> movieOpt = movieService.findMovieById(filmShowRequestDto.getMovieId());
        if (movieOpt.isEmpty()){
            throw new MovieNotFoundException("There is no movie with this id");
        }
        filmShow.setMovie(movieOpt.get());
        return filmShow;
    }


    @Override
    public Page<FilmShow> findFilmShowsPageByTitle(String title, Pageable pageable) {
        return filmShowDao.findFilmShowsPageByTitle(title,pageable);
    }


    @Override
    public Page<FilmShow> findFilmShowsPage(Pageable pageable) {
        return filmShowDao.findFilmShowsPage(pageable);
    }

    @Override
    public Optional<FilmShow> findFilmShow(FilmShowRequestDto filmShowRequestDto) {
        return filmShowDao.findFilmShowByDateAndFilmIdAndRoomId(
                filmShowRequestDto.getDateOfFilmScreening(),
                filmShowRequestDto.getMovieId(),
                filmShowRequestDto.getRoomId()
        );
    }
}
