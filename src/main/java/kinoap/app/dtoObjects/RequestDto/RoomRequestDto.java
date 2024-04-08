package kinoap.app.dtoObjects.RequestDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomRequestDto {
    @NotBlank
    private String roomNumber;
    @NotBlank
    private String roomType;
    @NotNull
    private long numberOfSeats;
}
