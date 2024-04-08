package kinoap.app.dao;

import kinoap.app.Entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDao {
    public void save(Client client);
    public void delete(long id);
    public void update(Client client);
    public List<Client> findAllClients();
    public Client findClientById(long id);
    public Optional<Client> findClientByEmail(String email);


}
