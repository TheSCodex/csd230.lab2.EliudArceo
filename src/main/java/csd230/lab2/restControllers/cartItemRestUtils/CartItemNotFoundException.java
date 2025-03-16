package csd230.lab2.restControllers.cartItemRestUtils;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(long id) {
        super("Could not find cart item " + id);
    }
}
