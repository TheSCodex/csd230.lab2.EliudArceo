package csd230.lab2.restControllers.cartRestUtils;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(long id) {

        super("Could not find cart " + id);
    }
}
