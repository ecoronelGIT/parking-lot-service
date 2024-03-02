package assessment.parkinglot.core.exception;

import assessment.parkinglot.common.exception.ApiException;
import assessment.parkinglot.common.exception.ApiExceptionResponse;
import assessment.parkinglot.common.exception.ErrorCode;
import assessment.parkinglot.common.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionResponse> apiException(ApiException exception, WebRequest request) {
        log.error(
                "--apiException --code: {} message: {} --status: {}",
                exception.getCode(), exception.getMessage(), exception.getStatus());

        String cause = Optional.ofNullable(exception.getCause()).map(Throwable::getMessage).orElseGet(String::new);
        ApiExceptionResponse response = new ApiExceptionResponse(exception.getCode(), exception.getMessage(), cause);
        return ResponseEntity.status(exception.status).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> allExceptions(Exception exception, WebRequest request) {
        log.error("--AccountApiExceptionHandler:AllExceptions --message: [{${}]--stackTrace: [{${}}]",
                exception.getMessage(), exception.getStackTrace());

        ApiExceptionResponse response = new ApiExceptionResponse(
                ErrorCode.INTERNAL_SERVER_ERROR,
                ErrorMessage.INTERNAL_SERVER_ERROR,
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
