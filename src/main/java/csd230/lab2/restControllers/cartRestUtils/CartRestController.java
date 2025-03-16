package csd230.lab2.restControllers.cartRestUtils;

import csd230.lab2.entities.*;
import csd230.lab2.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("rest/cart")
public class CartRestController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MagazineRepository magazineRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private DiscMagRepository discMagRepository;

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartRestController(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @GetMapping
    public List<Cart> getAllCarts() {
        return (List<Cart>) cartRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Cart> getCart(@PathVariable Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart == null){
            throw new CartNotFoundException(id);
        }
        return cart;
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartRepository.save(cart);
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id) {
        cartRepository.deleteById(id);
    }

    @PostMapping("/add-items")
    public Cart addItemsToCart(@RequestParam("itemIds") List<Long> itemIds, @RequestParam("itemType") String itemType, @RequestParam("cartId") Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        itemIds.forEach(itemId -> {
            switch (itemType) {
                case "BOOK":
                    bookRepository.findById(itemId).ifPresent(book -> {
                        book.setCart(cart);
                        cart.getItems().add(book);
                        bookRepository.save(book);
                    });
                    break;
                case "MAGAZINE":
                    magazineRepository.findById(itemId).ifPresent(magazine -> {
                        magazine.setCart(cart);
                        cart.getItems().add(magazine);
                        magazineRepository.save(magazine);
                    });
                    break;
                case "TICKET":
                    ticketRepository.findById(itemId).ifPresent(ticket -> {
                        ticket.setCart(cart);
                        cart.getItems().add(ticket);
                        ticketRepository.save(ticket);
                    });
                    break;
                case "DISCMAG":
                    discMagRepository.findById(itemId).ifPresent(discMag -> {
                        discMag.setCart(cart);
                        cart.getItems().add(discMag);
                        discMagRepository.save(discMag);
                    });
                    break;
            }
        });
        return cartRepository.save(cart);
    }

    @DeleteMapping("/remove-item/{cartId}/{itemId}")
    public void removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        cartItemRepository.findById(itemId).ifPresent(item -> {
            if (item.getCart() != null && item.getCart().getId().equals(cartId)) {
                item.setCart(null);
                cartItemRepository.save(item);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item does not belong to the specified cart.");
            }
        });
    }

}
