package kinoap.app.dtoObjects.RequestDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SnackRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String type;
    @NotNull
    @Min(value = 0, message = "min state is {value]")
    private Long state;
}
