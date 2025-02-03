package csd230.lab2;

import csd230.lab2.entities.Cart;
import csd230.lab2.entities.CartItem;
import csd230.lab2.repositories.CartItemRepository;
import csd230.lab2.repositories.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CartItemRepositoryTests {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void testCreateCartItem() {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem = cartItemRepository.save(cartItem);
        assertThat(cartItem.getId()).isNotNull();
    }

    @Test
    public void testReadCartItem() {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem = cartItemRepository.save(cartItem);

        Optional<CartItem> foundCartItem = cartItemRepository.findById(cartItem.getId());
        assertThat(foundCartItem).isPresent();
        assertThat(foundCartItem.get().getId()).isEqualTo(cartItem.getId());
    }

    @Test
    public void testUpdateCartItem() {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem = cartItemRepository.save(cartItem);

        cartItem = cartItemRepository.save(cartItem);
        Optional<CartItem> updatedCartItem = cartItemRepository.findById(cartItem.getId());
        assertThat(updatedCartItem).isPresent();
    }

    @Test
    public void testDeleteCartItem() {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem = cartItemRepository.save(cartItem);

        Long cartItemId = cartItem.getId();
        cartItemRepository.delete(cartItem);
        Optional<CartItem> deletedCartItem = cartItemRepository.findById(cartItemId);
        assertThat(deletedCartItem).isNotPresent();
    }
}