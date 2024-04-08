package kinoap.app.ExceptionResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SnackErrorResponse {
    public int status;
    private String message;
    private long timeStamp;
}
