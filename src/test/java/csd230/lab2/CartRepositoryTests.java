package csd230.lab2;

import csd230.lab2.entities.Cart;
import csd230.lab2.repositories.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void testCreateCart() {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);
        assertThat(cart.getId()).isNotNull();
    }

    @Test
    public void testReadCart() {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);
        Optional<Cart> foundCart = cartRepository.findById(cart.getId());
        assertThat(foundCart).isPresent();
        assertThat(foundCart.get().getId()).isEqualTo(cart.getId());
    }

    @Test
    public void testUpdateCart() {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);
        cart = cartRepository.save(cart);
        Optional<Cart> updatedCart = cartRepository.findById(cart.getId());
        assertThat(updatedCart).isPresent();
    }

    @Test
    public void testDeleteCart() {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);
        Long cartId = cart.getId();
        cartRepository.delete(cart);
        Optional<Cart> deletedCart = cartRepository.findById(cartId);
        assertThat(deletedCart).isNotPresent();
    }
}