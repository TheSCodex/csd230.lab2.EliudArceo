package csd230.lab2.restControllers.magazineRestUtils;

public class MagazineNotFoundException extends RuntimeException {
    public MagazineNotFoundException(Long id) {
        super("Could not find magazine " + id);
    }
}
