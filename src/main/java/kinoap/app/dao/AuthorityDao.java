package kinoap.app.dao;

import kinoap.app.Entity.Authority;

public interface AuthorityDao {
    public Authority findAuthorityByName(String name);
}
