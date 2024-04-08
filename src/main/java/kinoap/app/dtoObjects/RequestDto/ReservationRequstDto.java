package kinoap.app.dtoObjects.RequestDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;

@Data
public class ReservationRequstDto {
    @NotEmpty
    @Size(max = 50,message = "max {max}")
    private ArrayList<Integer> ticketIds;
    @Size(min = 0,message = "min {min}")
    private ArrayList<Integer> snackIds;
    @NotNull
    private Integer filmShowId;
}
