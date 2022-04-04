package id.ac.fksp.spmi.controller;

import id.ac.fksp.spmi.exception.AnnouncementException;
import id.ac.fksp.spmi.exception.UserException;
import id.ac.fksp.spmi.payload.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationHandler(MethodArgumentNotValidException validException){
        Map<String, String> errors = new HashMap<>();
        validException.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.badRequest().body(new ApiResponse<Map<String,String>>(
                false,
                "Bad Request",
                errors
        ));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> userExceptionHandler(UserException userException){
        return ResponseEntity.badRequest().body(new ApiResponse<String>(
           false,
           "Bad Request",
                userException.getMessage()
        ));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AnnouncementException.class)
    public ResponseEntity<?> userExceptionHandler(AnnouncementException announcementException){
        return ResponseEntity.badRequest().body(new ApiResponse<String>(
           false,
           "Bad Request",
                announcementException.getMessage()
        ));
    }
}
