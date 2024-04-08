package kinoap.app.dtoObjects.RequestDto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MovieRequestDto {
    @NotBlank
    private String title;
    @NotNull
    @Min(value = 0,message = "minimum is {value}")
    private long duration;
    @NotNull
    @Min(value = 0,message = "minimum rating is {value}")
    @Max(value = 10,message = "maximum rating is {value}")
    private long avgRating;
}


