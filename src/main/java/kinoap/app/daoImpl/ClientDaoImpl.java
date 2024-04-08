package kinoap.app.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import kinoap.app.Entity.Client;
import kinoap.app.dao.ClientDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientDaoImpl implements ClientDao {
    private EntityManager entityManager;

    public ClientDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Client client) {
        this.entityManager.persist(client);
    }

    @Override
    public void delete(long id) {
        Client client = entityManager.find(Client.class,id);
        this.entityManager.remove(client);
    }

    @Override
    public void update(Client client) {
        this.entityManager.merge(client);
    }

    @Override
    public List<Client> findAllClients() {
        TypedQuery<Client> clients = entityManager.createQuery("from clients", Client.class);
        return clients.getResultList();
    }

    @Override
    public Client findClientById(long id) {
        return entityManager.find(Client.class,id);
    }


    @Override
    public Optional<Client> findClientByEmail(String email) {
        TypedQuery<Client> typedQuery = entityManager.createQuery("from Client where email = :email", Client.class)
                .setParameter("email",email);
        return typedQuery.getResultList().stream().findFirst();
    }
}
