package kinoap.app.services;

import kinoap.app.Entity.Snack;
import kinoap.app.Entity.Ticket;
import kinoap.app.dtoObjects.RequestDto.SnackRequestDto;
import kinoap.app.dtoObjects.RequestDto.TicketRequestDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SnackService {
    public void saveSnack(SnackRequestDto snackRequestDto);
    public void deleteSnack(long id);
    public Snack updateSnack(long id,SnackRequestDto snackRequestDto);
    public Snack updateSnack(long id,Map<String,Object> snackEditVariables);
    public List<Snack> findAllSnacks();
    public Optional<Snack> findSnackById(long id);
    public Optional<Snack> findSnackByName(String name);
}
