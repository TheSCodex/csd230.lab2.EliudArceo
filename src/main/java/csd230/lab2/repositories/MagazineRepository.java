package csd230.lab2.repositories;

import csd230.lab2.entities.Book;
import csd230.lab2.entities.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {

    Magazine findById(long id);
    // Custom query to search by text in the title
    List<Magazine> findByTitleContaining(String text);

    // Custom query to search by price
    List<Magazine> findByPrice(double price);

    // Custom query to search by quantity
    List<Magazine> findByQuantity(int quantity);

    // Custom query to search by price greater than a specific value
    List<Magazine> findByPriceGreaterThan(double price);

    // Custom query to search by quantity less than a specific value
    List<Magazine> findByQuantityLessThan(int quantity);

    // Custom query to search by title and price
    List<Magazine> findByTitleAndPrice(String title, double price);

    // Custom query to search by title
    @Query("SELECT m FROM Magazine m WHERE m.title LIKE %:text%")
    List<Magazine> searchByTitle(@Param("text") String text);

}