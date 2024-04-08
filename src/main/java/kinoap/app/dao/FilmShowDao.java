package kinoap.app.dao;

import kinoap.app.Entity.FilmShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.spel.ast.OpAnd;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FilmShowDao {
    public void save(FilmShow filmShow);
    public void delete(long id);
    public void update(FilmShow filmShow);
    public FilmShow getFilmShowById(long id);
    public List<FilmShow> findAllFilmShows();
    public Page<FilmShow> findFilmShowsPage(Pageable pageable);
    public Page<FilmShow> findFilmShowsPageByTitle(String title, Pageable pageable);
    public Optional<FilmShow> findFilmShowByDateAndFilmIdAndRoomId(LocalDateTime date, long filmId, long roomId);
}
