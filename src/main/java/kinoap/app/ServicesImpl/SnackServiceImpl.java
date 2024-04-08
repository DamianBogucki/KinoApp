package kinoap.app.ServicesImpl;

import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import kinoap.app.Entity.Movie;
import kinoap.app.Entity.Snack;
import kinoap.app.dao.SnackDao;
import kinoap.app.dtoObjects.RequestDto.SnackRequestDto;
import kinoap.app.services.SnackService;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SnackServiceImpl implements SnackService {
    private SnackDao snackDao;

    public SnackServiceImpl(SnackDao snackDao) {
        this.snackDao = snackDao;
    }

    @Override
    @Transactional
    public void saveSnack(SnackRequestDto snackRequestDto) {
        Snack snack = new Snack();
        snack.setType(snackRequestDto.getType());
        snack.setName(snackRequestDto.getName());
        snack.setState(snackRequestDto.getState());
        snackDao.save(snack);
    }

    @Override
    @Transactional
    public void deleteSnack(long id) {
        snackDao.delete(id);
    }

    @Override
    @Transactional
    public Snack updateSnack(long id, SnackRequestDto snackRequestDto) {
        Snack snack = snackDao.findSnackById(id);
        snack.setType(snackRequestDto.getType());
        snack.setName(snackRequestDto.getName());
        snack.setState(snackRequestDto.getState());
        snackDao.update(snack);
        return snack;
    }

    @Override
    @Transactional
    public Snack updateSnack(long id, Map<String, Object> snackEditVariables) {
        Snack snack = snackDao.findSnackById(id);
        snackEditVariables.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(Snack.class,(String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,snack,value);
        });
        return snack;
    }

    @Override
    public List<Snack> findAllSnacks() {
        return snackDao.findAllSnacks();
    }

    @Override
    public Optional<Snack> findSnackById(long id) {
        Optional<Snack> snackOpt = Optional.ofNullable(snackDao.findSnackById(id));
        return snackOpt;
    }

    @Override
    public Optional<Snack> findSnackByName(String name) {
        return snackDao.findSnackByName(name);
    }
}
