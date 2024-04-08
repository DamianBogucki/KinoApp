package kinoap.app.daoImpl;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import kinoap.app.Entity.FilmShow;
import kinoap.app.Entity.Movie;
import kinoap.app.dao.FilmShowDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class FilmShowDaoImpl implements FilmShowDao {
    private EntityManager entityManager;

    public FilmShowDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(FilmShow filmShow) {
        entityManager.persist(filmShow);
    }

    @Override
    public void delete(long id) {
        FilmShow filmShow = entityManager.find(FilmShow.class,id);
        entityManager.remove(filmShow);
    }

    @Override
    public void update(FilmShow filmShow) {
        entityManager.merge(filmShow);
    }

    @Override
    public FilmShow getFilmShowById(long id) {
        return entityManager.find(FilmShow.class,id);
    }


    @Override
    public List<FilmShow> findAllFilmShows() {
        TypedQuery<FilmShow> filmShows = entityManager.createQuery("from FilmShow", FilmShow.class);
        return filmShows.getResultList();
    }

    @Override
    public Page<FilmShow> findFilmShowsPage(Pageable pageable) {
        String columnNameAndOrderWay = pageable.getSort().get().findFirst().get().getProperty() + " " + pageable.getSort().get().findFirst().get().getDirection().name();

        TypedQuery<FilmShow> filmShows = entityManager.createQuery("select fs from FilmShow fs LEFT JOIN fs.movie m order by fs.dateOfFilmScreening, " +
                        columnNameAndOrderWay, FilmShow.class)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize());

        TypedQuery<Long> countFilmShows = entityManager.createQuery("select count(fs.id) from FilmShow fs", Long.class);
        long countFS = countFilmShows.getSingleResult();

        return new PageImpl<>(filmShows.getResultList(),pageable,countFS);
    }

    @Override
    public Page<FilmShow> findFilmShowsPageByTitle(String title, Pageable pageable) {
        String columnNameAndOrderWay = pageable.getSort().get().findFirst().get().getProperty() + " " + pageable.getSort().get().findFirst().get().getDirection().name();

        TypedQuery<FilmShow> filmShows = entityManager.createQuery("select fs from FilmShow fs LEFT JOIN fs.movie m where m.title like :title" +
                        " order by fs.dateOfFilmScreening, " + columnNameAndOrderWay, FilmShow.class)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .setParameter("title","%"+title+"%");

        TypedQuery<Long> countFilmShows = entityManager.createQuery("select count(fs.id) from FilmShow fs LEFT JOIN fs.movie m where m.title like :title", Long.class)
                .setParameter("title","%"+title+"%");
        long countFS = countFilmShows.getSingleResult();

        return new PageImpl<>(filmShows.getResultList(),pageable,countFS);

    }

    @Override
    public Optional<FilmShow> findFilmShowByDateAndFilmIdAndRoomId(LocalDateTime date, long movieId, long roomId) {
        TypedQuery<FilmShow> theQuery = entityManager.createQuery(
                "select fs from FilmShow fs LEFT JOIN fs.movie m LEFT JOIN fs.room r where fs.dateOfFilmScreening = :screening and m.id = :movieId and r.id = :roomId",
                        FilmShow.class)
                .setParameter("screening",date)
                .setParameter("movieId",movieId)
                .setParameter("roomId",roomId);
        return theQuery.getResultList().stream().findFirst();
    }
}
