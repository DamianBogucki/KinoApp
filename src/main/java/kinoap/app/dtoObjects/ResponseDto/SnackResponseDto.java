package kinoap.app.dtoObjects.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SnackResponseDto {
    private long id;
    private String name;
    private String type;
    private long state;
}
