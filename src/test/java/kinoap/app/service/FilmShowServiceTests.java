package kinoap.app.service;

import kinoap.app.Entity.FilmShow;
import kinoap.app.Entity.Movie;
import kinoap.app.Entity.Room;
import kinoap.app.ServicesImpl.FilmShowServiceImpl;
import kinoap.app.ServicesImpl.MovieServiceImpl;
import kinoap.app.ServicesImpl.RoomServiceImpl;
import kinoap.app.daoImpl.FilmShowDaoImpl;
import kinoap.app.dtoObjects.RequestDto.FilmShowRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilmShowServiceTests {
    @Mock
    private FilmShowDaoImpl filmShowDao;
    @Mock
    private MovieServiceImpl movieService;
    @Mock
    private RoomServiceImpl roomService;
    @InjectMocks
    private FilmShowServiceImpl filmShowService;

    @Test
    public void FilmShowService_SaveFilmShow_ReturnVoid() {
        FilmShowRequestDto filmShowRequestDto = new FilmShowRequestDto();
        filmShowRequestDto.setDateOfFilmScreening(LocalDateTime.of(2024, 5, 14, 13, 30));
        filmShowRequestDto.setRoomId(1);
        filmShowRequestDto.setMovieId(1);

        when(roomService.findRoomById(filmShowRequestDto.getRoomId())).thenReturn(Optional.of(new Room()));
        when(movieService.findMovieById(filmShowRequestDto.getRoomId())).thenReturn(Optional.of(new Movie()));
        doNothing().when(filmShowDao).save(Mockito.any(FilmShow.class));

        assertAll(() -> filmShowService.saveFilmShow(filmShowRequestDto));
    }

    @Test
    public void FilmShowService_DeleteFilmShow_ReturnVoid() {
        int filmShowId = 1;
        doNothing().when(filmShowDao).delete(filmShowId);
        assertAll(() -> filmShowService.deleteFilmShow(filmShowId));
    }

    @Test
    public void FilmShowService_FindAllFilmShows_ReturnFilmShows() {
        List<FilmShow> filmShows = Mockito.mock(List.class);
        when(filmShowDao.findAllFilmShows()).thenReturn(filmShows);
        List<FilmShow> filmShowsGet = filmShowService.findAllFilmShows();
        assertThat(filmShowsGet).isNotNull();
    }

    @Test
    public void FilmShowService_findFilmShowsPageByTitle_ReturnPageOfFilmShows() {
        String title = "Hobbit";
        int page = 0;
        int size = 10;
        Page<FilmShow> pageOfFilmShows = Mockito.mock(Page.class);
        when(filmShowDao.findFilmShowsPageByTitle(Mockito.any(String.class), Mockito.any(Pageable.class))).thenReturn(pageOfFilmShows);
        Page<FilmShow> filmShowsPage = filmShowService.findFilmShowsPageByTitle(title, PageRequest.of(page, size));
        assertThat(filmShowsPage.getContent()).isNotNull();
    }

    @Test
    public void MovieService_findMoviesPage_ReturnMovies() {
        String title = "Hobbit";
        int page = 0;
        int size = 10;
        Page<FilmShow> pageOfFilmShows = Mockito.mock(Page.class);
        when(filmShowDao.findFilmShowsPage(Mockito.any(Pageable.class))).thenReturn(pageOfFilmShows);
        Page<FilmShow> filmShowsPage = filmShowService.findFilmShowsPage(PageRequest.of(page, size));
        assertThat(filmShowsPage.getContent()).isNotNull();
    }

    @Test
    public void FilmShowService_findFilmShowById_ReturnOptFilmShow() {
        int filmShowId = 1;
        FilmShow filmShow = new FilmShow();
        when(filmShowDao.getFilmShowById(filmShowId)).thenReturn(filmShow);
        FilmShow filmShowOpt = filmShowService.findFilmShowById(filmShowId).get();
        assertThat(filmShowOpt).isNotNull();
    }

    @Test
    public void FilmShowService_UpdateFilmShow_ReturnFilmShow() {
        int filmShowId = 1;
        FilmShowRequestDto filmShowRequestDto = new FilmShowRequestDto();
        filmShowRequestDto.setDateOfFilmScreening(LocalDateTime.of(2024, 5, 14, 13, 30));
        filmShowRequestDto.setMovieId(1);
        filmShowRequestDto.setRoomId(1);

        when(filmShowDao.getFilmShowById(filmShowId)).thenReturn(new FilmShow());
        when(movieService.findMovieById(filmShowRequestDto.getMovieId())).thenReturn(Optional.of(new Movie()));
        when(roomService.findRoomById(filmShowRequestDto.getRoomId())).thenReturn(Optional.of(new Room()));

        FilmShow filmShowUpdated = filmShowService.updateFilmShow(filmShowId, filmShowRequestDto);
        assertThat(filmShowUpdated).isNotNull();
        assertThat(filmShowUpdated.getMovie().getId()).isEqualTo(filmShowRequestDto.getMovieId());
    }

    @Test
    public void FilmShowService_FindFilmShow_ReturnFilmShow() {
        FilmShowRequestDto filmShowRequestDto = new FilmShowRequestDto();
        filmShowRequestDto.setDateOfFilmScreening(LocalDateTime.of(2024, 5, 14, 13, 30));
        filmShowRequestDto.setMovieId(1);
        filmShowRequestDto.setRoomId(1);

        FilmShow filmShow = new FilmShow();
        when(filmShowDao.findFilmShowByDateAndFilmIdAndRoomId(filmShowRequestDto.getDateOfFilmScreening(),
                filmShowRequestDto.getMovieId(),
                filmShowRequestDto.getRoomId())).thenReturn(Optional.of(filmShow));
        FilmShow filmShowOpt = filmShowService.findFilmShow(filmShowRequestDto).get();
        assertThat(filmShowOpt).isNotNull();
    }
}