package csd230.lab2.restControllers.ticketRestUtils;

import csd230.lab2.entities.Ticket;
import csd230.lab2.repositories.TicketRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/ticket")
public class TicketRestController {

    private final TicketRepository ticketRepository;

    public TicketRestController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping()
    List<Ticket> all() {
        return (List<Ticket>) ticketRepository.findAll();
    }

    @GetMapping
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Ticket getTicket(@PathVariable int id) {
        Ticket ticket = ticketRepository.findById(id);
        if (ticket == null) {
            throw new TicketNotFoundException((long) id);
        }
        return ticket;
    }

    @PostMapping()
    Ticket newTicket(@RequestBody Ticket newTicket) {
        newTicket.setDescription("Ticket: " + newTicket.getText());
        return ticketRepository.save(newTicket);
    }

    @PutMapping("/{id}")
    Ticket replaceTicket(@RequestBody Ticket newTicket, @PathVariable Long id) {
        return ticketRepository.findById(id)
                .map(ticket -> {
                    ticket.setText(newTicket.getText());
                    ticket.setDescription("Ticket: " + newTicket.getText());
                    ticket.setPrice(newTicket.getPrice());
                    ticket.setQuantity(newTicket.getQuantity());
                    return ticketRepository.save(ticket);
                })
                .orElseGet(() -> ticketRepository.save(newTicket));
    }

    @DeleteMapping("/{id}")
    void deleteTicket(@PathVariable Long id) {
        ticketRepository.deleteById(id);
    }
}
