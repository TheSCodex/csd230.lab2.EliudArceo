package csd230.lab2.repositories;

import csd230.lab2.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {

    // Custom query to search by text in the title
    List<Publication> findByTitleContaining(String text);

    // Custom query to search by title and author
    List<Publication> findByTitle(String title);

    // Custom query to search by title
    @Query("SELECT p FROM Publication p WHERE p.title LIKE %:text%")
    List<Publication> searchByTitle(@Param("text") String text);
}