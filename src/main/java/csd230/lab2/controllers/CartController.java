package csd230.lab2.controllers;

import csd230.lab2.entities.Cart;
import csd230.lab2.repositories.CartItemRepository;
import csd230.lab2.entities.*;
import csd230.lab2.repositories.*;
import csd230.lab2.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MagazineRepository magazineRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private DiscMagRepository discMagRepository;

    private final CartItemRepository cartItemRepository;
    CartRepository cartRepository;

    public CartController(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @GetMapping
    public String cart(Model model) {
        Iterable<Cart> allCarts = cartRepository.findAll();
        // in a real app, we would get the cart for the current user
        Cart firstCart = allCarts.iterator().next();
        double total = firstCart.getItems().stream()
                .mapToDouble(item -> item.getPrice())
                .sum();

        model.addAttribute("cart", firstCart);
        model.addAttribute("total", total);

        return "cart/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        Iterable<Cart> allCarts = cartRepository.findAll();
        // in a real app, we would get the cart for the current user
        Cart firstCart = allCarts.iterator().next();
        double total = firstCart.getItems().stream()
                .mapToDouble(item -> item.getPrice())
                .sum();

        model.addAttribute("cart", firstCart);
        model.addAttribute("total", total);

        return "cart/checkout";
    }

    @GetMapping("/add-cart")
    public String cartForm(Model model) {
        model.addAttribute("cart", new Cart());
        return "add-cart";
    }

    @PostMapping("/add-cart")
    public String cartSubmit(@ModelAttribute Cart cart, Model model) {
        model.addAttribute("cart", cart);
        cartRepository.save(cart);
        model.addAttribute("carts", cartRepository.findAll());
        return "redirect:/cart";
    }

//    @GetMapping("/edit-cart")
//    public String edit_cart(@RequestParam(value = "id", required = false) Integer id, Model model) {
//        model.addAttribute("cart", cartRepository.findById(id));
//        return "redirect:/cart";
//    }
//    @PostMapping("/edit-cart")
//    public String edit_cartSubmit(@ModelAttribute CartEntity cart, Model model) {
//        model.addAttribute("cart", cart);
//        cartRepository.save(cart);
//        model.addAttribute("carts", cartRepository.findAll());
//        return "redirect:/cart";
//    }

    @PostMapping("/selection")
    public String processSelection(@RequestParam("selectedCarts") List<Integer> selectedCartIds) {
        // Process the selected cart list here...
        System.out.println(selectedCartIds);
        for (Integer id : selectedCartIds) {
            Cart cart = cartRepository.findById(id);
            cartRepository.delete(cart);
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/add")
    public String addItemsToCart(@RequestParam("selectedItems") String selectedItemIds, @RequestParam("itemType") String itemType) {
        List<Long> selectedIds = Arrays.stream(selectedItemIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        List<Cart> carts = (List<Cart>) cartRepository.findAll();
        Cart cart;
        if (carts.isEmpty()) {
            cart = cartRepository.save(new Cart());
        } else {
            cart = carts.get(0);
        }
        for (Long itemId : selectedIds) {
            switch (itemType) {
                case "BOOK":
                    bookRepository.findById(itemId).ifPresent(book -> {
                        book.setCart(cart);
                        bookRepository.save(book);
                    });
                    break;
                case "MAGAZINE":
                    magazineRepository.findById(itemId).ifPresent(magazine -> {
                        magazine.setCart(cart);
                        magazineRepository.save(magazine);
                    });
                    break;
                case "TICKET":
                    ticketRepository.findById(itemId).ifPresent(ticket -> {
                        ticket.setCart(cart);
                        ticketRepository.save(ticket);
                    });
                    break;
                case "DISCMAG":
                    discMagRepository.findById(itemId).ifPresent(discMag -> {
                        discMag.setCart(cart);
                        discMagRepository.save(discMag);
                    });
                    break;
            }
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove-cart")
    public String removeItemFromCart(@RequestParam("cartItemId") Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItem.setCart(null);
        cartItemRepository.save(cartItem);
        return "redirect:/cart";
    }
}