package kinoap.app.dtoObjects.ResponseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDto {
    private long id;
    private List<TicketResponseDto> tickets;
    private List<SnackResponseDto> snacks;
    private FilmShowResponseDto filmShow;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateOfBooking;
}
