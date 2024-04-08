package kinoap.app.dtoObjects.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RoomResponseDto {
    private long id;
    private String roomNumber;
    private String roomType;
    private long numberOfSeats;
}
