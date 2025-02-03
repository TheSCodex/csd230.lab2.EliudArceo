package csd230.lab2;

import csd230.lab2.entities.Cart;
import csd230.lab2.entities.CartItem;
import csd230.lab2.entities.Magazine;
import csd230.lab2.repositories.CartRepository;
import csd230.lab2.repositories.MagazineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MagazineRepositoryTests {

    @Autowired
    private MagazineRepository magazineRepository;

    @Autowired
    private CartRepository cartRepository;

    @Test
    void testAddMagazineToCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);

        Magazine magazine = new Magazine(19.99, 5, "A great magazine", "Tech Monthly", 50, 3, LocalDate.of(2023, 10, 1));
        cart.addItem(magazine);
        cartRepository.save(cart);

        Optional<Cart> savedCart = cartRepository.findById(cart.getId());
        assertThat(savedCart).isPresent();
        assertThat(savedCart.get().getItems()).hasSize(1);

        CartItem savedItem = savedCart.get().getItems().iterator().next();
        assertThat(savedItem).isInstanceOf(Magazine.class);

        Magazine savedMagazine = (Magazine) savedItem;
        assertThat(savedMagazine.getTitle()).isEqualTo("Tech Monthly");
        assertThat(savedMagazine.getOrderQty()).isEqualTo(3);
    }

    @Test
    void testRemoveMagazineFromCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);

        Magazine magazine = new Magazine(19.99, 5, "A great magazine", "Tech Monthly", 50, 3, LocalDate.of(2023, 10, 1));
        magazineRepository.save(magazine);

        cart.addItem(magazine);
        cartRepository.save(cart);

        cart.getItems().remove(magazine);
        magazine.setCart(null);
        cartRepository.save(cart);

        Optional<Cart> updatedCart = cartRepository.findById(cart.getId());
        assertThat(updatedCart).isPresent();
        assertThat(updatedCart.get().getItems()).isEmpty();
    }

    @Test
    void testUpdateMagazineDetailsInCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);

        Magazine magazine = new Magazine(19.99, 5, "A great magazine", "Tech Monthly", 50, 3, LocalDate.of(2023, 10, 1));
        magazineRepository.save(magazine);
        cart.addItem(magazine);
        cartRepository.save(cart);

        magazine.setTitle("Advanced Tech Monthly");
        magazine.setOrderQty(10);
        magazineRepository.save(magazine);
        cartRepository.save(cart);

        Optional<Cart> updatedCart = cartRepository.findById(cart.getId());
        assertThat(updatedCart).isPresent();
        Magazine updatedMagazine = (Magazine) updatedCart.get().getItems().iterator().next();
        assertThat(updatedMagazine.getTitle()).isEqualTo("Advanced Tech Monthly");
        assertThat(updatedMagazine.getOrderQty()).isEqualTo(10);
    }

    @Test
    void testSaveAndFindMagazineById() {
        Magazine magazine = new Magazine(9.99, 10, "A magazine description", "Science Weekly", 100, 5, LocalDate.of(2023, 10, 1));
        magazineRepository.save(magazine);

        Optional<Magazine> foundMagazine = magazineRepository.findById(magazine.getId());
        assertThat(foundMagazine).isPresent();
        assertThat(foundMagazine.get().getTitle()).isEqualTo("Science Weekly");
        assertThat(foundMagazine.get().getOrderQty()).isEqualTo(5);
    }

    @Test
    void testFindAllMagazines() {
        magazineRepository.deleteAll();
        Magazine magazine1 = new Magazine(9.99, 10, "Magazine 1 description", "Magazine 1", 100, 5, LocalDate.of(2023, 10, 1));
        Magazine magazine2 = new Magazine(14.99, 20, "Magazine 2 description", "Magazine 2", 50, 10, LocalDate.of(2023, 10, 1));
        magazineRepository.save(magazine1);
        magazineRepository.save(magazine2);

        Iterable<Magazine> magazines = magazineRepository.findAll();
        assertThat(magazines).hasSize(2);
    }

    @Test
    void testDeleteMagazine() {
        Magazine magazine = new Magazine(9.99, 10, "Magazine description", "Science Today", 100, 5, LocalDate.of(2023, 10, 1));
        magazineRepository.save(magazine);

        magazineRepository.delete(magazine);

        Optional<Magazine> deletedMagazine = magazineRepository.findById(magazine.getId());
        assertThat(deletedMagazine).isNotPresent();
    }
}