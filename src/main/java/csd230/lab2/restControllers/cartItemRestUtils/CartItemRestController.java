package csd230.lab2.restControllers.cartItemRestUtils;

import csd230.lab2.entities.CartItem;
import csd230.lab2.repositories.CartItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/cart-item")
public class CartItemRestController {
    private final CartItemRepository cartItemRepository;

    public CartItemRestController(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @GetMapping()
    List<CartItem> all() {
        return (List<CartItem>) cartItemRepository.findAll();
    }

    @GetMapping
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public CartItem getCartItem(@PathVariable int id) {
        CartItem cartItem = cartItemRepository.findById(id);
        if (cartItem == null) {
            throw new CartItemNotFoundException((long) id);
        }
        return cartItem;
    }

    @PostMapping()
    CartItem newCartItem(@RequestBody CartItem newCartItem) {
        return cartItemRepository.save(newCartItem);
    }

    @DeleteMapping("/{id}")
    void deleteCartItem(@PathVariable Long id) {
        cartItemRepository.deleteById(id);
    }
}
