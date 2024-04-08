package kinoap.app.dtoObjects.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TicketResponseDto {
    private long id;
    private String type;
    private double price;
}
