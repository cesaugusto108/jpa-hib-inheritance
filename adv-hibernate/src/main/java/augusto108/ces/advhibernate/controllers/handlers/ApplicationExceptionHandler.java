package augusto108.ces.advhibernate.controllers.handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.NoResultException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler({NoResultException.class, NoHandlerFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body(new ErrorResponse(e.toString(), e.getMessage(), HttpStatus.NOT_FOUND));
    }

    public static class ErrorResponse {

        private final String error;
        private final String message;
        private final HttpStatus status;
        private final int statusCode;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd/MM/yy")
        private final LocalDateTime timestamp;

        public ErrorResponse(String error, String message, HttpStatus status) {
            this.error = error;
            this.message = message;
            this.status = status;
            this.statusCode = status.value();
            this.timestamp = LocalDateTime.now();
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }
}
