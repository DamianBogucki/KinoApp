package kinoap.app.service;

import kinoap.app.Entity.Snack;
import kinoap.app.ServicesImpl.SnackServiceImpl;
import kinoap.app.daoImpl.SnackDaoImpl;
import kinoap.app.dtoObjects.RequestDto.SnackRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SnackServiceTests {
    @Mock
    private SnackDaoImpl snackDao;
    @InjectMocks
    private SnackServiceImpl snackService;

    @Test
    public void SnackService_SaveSnack_ReturnVoid() {
        SnackRequestDto snackRequestDto = new SnackRequestDto();
        snackRequestDto.setName("Popcorn");
        snackRequestDto.setType("salt");
        snackRequestDto.setState((long)10);

        doNothing().when(snackDao).save(Mockito.any(Snack.class));

        assertAll(() -> snackService.saveSnack(snackRequestDto));
    }

    @Test
    public void SnackService_DeleteSnack_ReturnVoid() {
        int SnackId = 1;
        doNothing().when(snackDao).delete(SnackId);
        assertAll(() -> snackService.deleteSnack(SnackId));
    }

    @Test
    public void SnackService_UpadateAllFieldsOfSnack_RetrunSnack() {
        int snackId = 1;
        SnackRequestDto snackRequestDto = new SnackRequestDto();
        snackRequestDto.setName("Popcorn");
        snackRequestDto.setType("salt");
        snackRequestDto.setState((long)10);

        Snack snack = new Snack("Pepsi","drink",100);

        when(snackDao.findSnackById(snackId)).thenReturn(snack);
        doNothing().when(snackDao).update(Mockito.any(Snack.class));

        Snack snackUpdated = snackService.updateSnack(snackId, snackRequestDto);

        assertThat(snackUpdated).isNotNull();
        assertThat(snackUpdated.getName()).isEqualTo(snackRequestDto.getName());
    }

    @Test
    public void SnackService_UpadateSomeFieldsOfSnack_ReturnSnack() {
        int snackId = 1;
        HashMap<String, Object> newData = new HashMap<>();
        newData.put("state",200);


        Snack snack = new Snack("Pepsi","drink",100);
        when(snackDao.findSnackById(snackId)).thenReturn(snack);
        Snack snackUpdated = snackService.updateSnack(snackId, newData);
        assertThat(snackUpdated).isNotNull();
        assertThat(snackUpdated.getState()).isEqualTo(newData.get("state"));
    }

    @Test
    public void SnackService_FindAllSnacks_ReturnSnacks() {
        List<Snack> snacks = Mockito.mock(List.class);
        when(snackDao.findAllSnacks()).thenReturn(snacks);

        List<Snack> snacksGet = snackService.findAllSnacks();
        assertThat(snacksGet).isNotNull();
    }

    @Test
    public void SnackService_FindSnackById_ReturnOptSnack() {
        int snackId = 1;

        when(snackDao.findSnackById(snackId)).thenReturn(new Snack());
        Snack snackOpt = snackService.findSnackById(snackId).get();
        assertThat(snackOpt).isNotNull();
    }

    @Test
    public void SnackService_FindSnackByName_ReturnSnack() {
        String snackName = "Pepsi";

        when(snackDao.findSnackByName(snackName)).thenReturn(Optional.of(new Snack()));
        Snack snackOpt = snackService.findSnackByName(snackName).get();
        assertThat(snackOpt).isNotNull();
    }
}
