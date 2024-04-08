package kinoap.app.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import kinoap.app.Entity.FilmShow;
import kinoap.app.Entity.Movie;
import kinoap.app.dao.MovieDao;
import kinoap.app.dtoObjects.Mapper;
import kinoap.app.dtoObjects.RequestDto.MovieRequestDto;
import kinoap.app.dtoObjects.ResponseDto.MovieResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.QTypeContributor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieDaoImpl implements MovieDao {
    private EntityManager entityManager;

    public MovieDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Movie movie) {
        entityManager.persist(movie);
    }

    @Override
    public void delete(long id) {
        Movie movie = entityManager.find(Movie.class,id);
        this.entityManager.remove(movie);
    }

    @Override
    public void update(Movie movie) {
        this.entityManager.merge(movie);
    }



    @Override
    public Movie findMovieById(long id) {
        return entityManager.find(Movie.class,id);
    }


    @Override
    public List<Movie> findAllMovies() {
        TypedQuery<Movie> theQuery = entityManager.createQuery("from Movie", Movie.class);
        return theQuery.getResultList();
    }

    @Override
    public Optional<Movie> findMovieByTitle(String title) {
        TypedQuery<Movie> theQuery = entityManager.createQuery("from Movie where title=:title", Movie.class).setParameter("title", title);
        return theQuery.getResultList().stream().findFirst();
    }

    @Override
    public Page<Movie> findMoviesPage(Pageable pageable) {
        String columnNameAndOrderWay = pageable.getSort().get().findFirst().get().getProperty() + " " + pageable.getSort().get().findFirst().get().getDirection().name();

        TypedQuery<Movie> theQuery = entityManager.createQuery("select m from Movie m order by " + columnNameAndOrderWay, Movie.class)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize());

        TypedQuery<Long> countMovies = entityManager.createQuery("select count(m.id) from Movie m",Long.class);
        long moviesCount = (Long) countMovies.getSingleResult();

        return new PageImpl<>(theQuery.getResultList(),pageable,moviesCount);
    }

    @Override
    public Page<Movie> findMoviesPageByTitle(String title, Pageable pageable) {
        String columnNameAndOrderWay = pageable.getSort().get().findFirst().get().getProperty() + " " + pageable.getSort().get().findFirst().get().getDirection().name();

        TypedQuery<Movie> theQuery = entityManager.createQuery("select m from Movie m where m.title like :title order by "
                        + columnNameAndOrderWay, Movie.class)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .setParameter("title","%"+title+"%");

        TypedQuery<Long> countMovies = entityManager.createQuery("select count(m.id) from Movie m where m.title like :title",Long.class)
                .setParameter("title","%"+title+"%");
        long moviesCount = (Long) countMovies.getSingleResult();

        return new PageImpl<>(theQuery.getResultList(),pageable,moviesCount);
    }
}
