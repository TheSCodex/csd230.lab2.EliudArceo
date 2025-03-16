package csd230.lab2.restControllers.publicationRestUtils;

public class PublicationNotFoundException extends RuntimeException {
    public PublicationNotFoundException(long id) {

        super("Could not find publication " + id);
    }
}
