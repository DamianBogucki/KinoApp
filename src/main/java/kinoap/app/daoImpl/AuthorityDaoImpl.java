package kinoap.app.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import kinoap.app.Entity.Authority;
import kinoap.app.dao.AuthorityDao;
import org.springframework.stereotype.Repository;

@Repository

public class AuthorityDaoImpl implements AuthorityDao {
    private EntityManager entityManager;

    public AuthorityDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Authority findAuthorityByName(String name) {
        TypedQuery<Authority> typedQuery = entityManager.createQuery("from Authority where name = :name", Authority.class)
                .setParameter("name",name);
        return typedQuery.getSingleResult();
    }
}
