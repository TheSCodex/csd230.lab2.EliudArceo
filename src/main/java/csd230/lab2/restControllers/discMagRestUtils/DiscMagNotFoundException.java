package csd230.lab2.restControllers.discMagRestUtils;

public class DiscMagNotFoundException extends RuntimeException {
    public DiscMagNotFoundException(long id) {
        super("Could not find discMag " + id);
    }
}
