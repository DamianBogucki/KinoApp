package kinoap.app.dao;

import kinoap.app.Entity.Room;
import kinoap.app.Entity.Snack;

import java.util.List;
import java.util.Optional;

public interface SnackDao {
    public void save(Snack snack);
    public void delete(long id);
    public void update(Snack snack);
    public List<Snack> findAllSnacks();
    public Snack findSnackById(long id);
    public Optional<Snack> findSnackByName(String name);
}
