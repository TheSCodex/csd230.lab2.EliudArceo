package csd230.lab2.restControllers.ticketRestUtils;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(long id) {

      super("Could not find ticket " + id);
    }
}
