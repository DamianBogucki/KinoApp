package kinoap.app.dtoObjects.RequestDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TicketRequestDto {
    @NotBlank
    private String type;
    @NotNull
    @Min(value = 0, message = "min price is {value}")
    private double price;
}
