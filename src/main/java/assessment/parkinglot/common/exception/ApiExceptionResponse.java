package assessment.parkinglot.common.exception;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiExceptionResponse {
    @NotNull
    private String code;
    @NotNull
    private String message;
    private String cause;

}
