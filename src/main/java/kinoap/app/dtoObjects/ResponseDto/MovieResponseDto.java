package kinoap.app.dtoObjects.ResponseDto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MovieResponseDto {
    private long id;
    private String title;
    private long duration;
    private long avgRating;
}
