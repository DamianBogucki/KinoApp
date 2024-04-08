package kinoap.app.dtoObjects.ResponseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class FilmShowResponseDto {
    private long id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dateOfFilmScreening;
    private MovieResponseDto movie;
    private RoomResponseDto room;
}
