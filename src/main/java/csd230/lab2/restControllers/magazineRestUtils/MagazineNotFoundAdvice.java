package csd230.lab2.restControllers.magazineRestUtils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MagazineNotFoundAdvice {
    @ExceptionHandler(MagazineNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String magazineNotFoundHandler(MagazineNotFoundException ex) {
        return ex.getMessage();
    }
}
