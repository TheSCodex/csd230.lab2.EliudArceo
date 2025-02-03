package csd230.lab2.repositories;


import csd230.lab2.entities.DiscMag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscMagRepository extends JpaRepository<DiscMag, Long> {
    DiscMag findById(long id);
}