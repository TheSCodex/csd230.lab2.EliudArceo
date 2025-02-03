package csd230.lab2;

import csd230.lab2.entities.Ticket;
import csd230.lab2.repositories.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TicketRepositoryTests {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void testSaveAndFindById() {
        Ticket ticket = new Ticket();
        ticket.setText("Test Ticket");
        ticketRepository.save(ticket);

        Optional<Ticket> foundTicket = ticketRepository.findById(ticket.getId());
        assertThat(foundTicket).isPresent();
        assertThat(foundTicket.get().getText()).isEqualTo("Test Ticket");
    }

    @Test
    void testFindAll() {
        ticketRepository.deleteAll();
        Ticket ticket1 = new Ticket();
        ticket1.setText("Test Ticket 1");
        ticketRepository.save(ticket1);

        Ticket ticket2 = new Ticket();
        ticket2.setText("Test Ticket 2");
        ticketRepository.save(ticket2);

        Iterable<Ticket> tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(2);
    }

    @Test
    void testDeleteTicket() {
        Ticket ticket = new Ticket();
        ticket.setText("Test Ticket");
        ticketRepository.save(ticket);

        ticketRepository.delete(ticket);

        Optional<Ticket> deletedTicket = ticketRepository.findById(ticket.getId());
        assertThat(deletedTicket).isNotPresent();
    }
}