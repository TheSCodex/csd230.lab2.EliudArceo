package csd230.lab2.restControllers.bookRestUtils;

public class BookNotFoundException extends RuntimeException{

    BookNotFoundException(Long id) {
        super("Could not find book " + id);
    }
}
