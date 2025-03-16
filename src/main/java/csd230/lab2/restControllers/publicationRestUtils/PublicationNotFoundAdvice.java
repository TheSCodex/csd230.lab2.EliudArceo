package csd230.lab2.restControllers.publicationRestUtils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PublicationNotFoundAdvice {
    @ExceptionHandler(PublicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String publicationNotFoundHandler(PublicationNotFoundException ex) {
        return ex.getMessage();
    }
}
