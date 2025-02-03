package csd230.lab2.repositories;

import csd230.lab2.entities.Magazine;
import csd230.lab2.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findById(long id);
    // Custom query to search by text
    List<Ticket> findByTextContaining(String text);

    // Custom query to search by text
    @Query("SELECT t FROM Ticket t WHERE t.text LIKE %:text%")
    List<Ticket> searchByText(@Param("text") String text);
}