package csd230.lab2;

import csd230.lab2.entities.Cart;
import csd230.lab2.entities.Publication;
import csd230.lab2.repositories.CartRepository;
import csd230.lab2.repositories.PublicationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PublicationRepositoryTests {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void testCreatePublication() {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        Publication publication = new Publication();
        publication.setTitle("Sample Title");
        publication.setCopies(10);
        publication.setCart(cart);
        publication = publicationRepository.save(publication);
        assertThat(publication.getId()).isNotNull();
    }

    @Test
    public void testReadPublication() {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        Publication publication = new Publication();
        publication.setTitle("Sample Title");
        publication.setCopies(10);
        publication.setCart(cart);
        publication = publicationRepository.save(publication);

        Optional<Publication> foundPublication = publicationRepository.findById(publication.getId());
        assertThat(foundPublication).isPresent();
        assertThat(foundPublication.get().getId()).isEqualTo(publication.getId());
    }

    @Test
    public void testUpdatePublication() {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        Publication publication = new Publication();
        publication.setTitle("Sample Title");
        publication.setCopies(10);
        publication.setCart(cart);
        publication = publicationRepository.save(publication);

        publication.setTitle("Updated Title");
        publication = publicationRepository.save(publication);
        Optional<Publication> updatedPublication = publicationRepository.findById(publication.getId());
        assertThat(updatedPublication).isPresent();
        assertThat(updatedPublication.get().getTitle()).isEqualTo("Updated Title");
    }

    @Test
    public void testDeletePublication() {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        Publication publication = new Publication();
        publication.setTitle("Sample Title");
        publication.setCopies(10);
        publication.setCart(cart);
        publication = publicationRepository.save(publication);

        Long publicationId = publication.getId();
        publicationRepository.delete(publication);
        Optional<Publication> deletedPublication = publicationRepository.findById(publicationId);
        assertThat(deletedPublication).isNotPresent();
    }
}