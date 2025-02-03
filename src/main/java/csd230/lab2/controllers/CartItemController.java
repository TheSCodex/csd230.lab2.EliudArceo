package csd230.lab2.controllers;

import csd230.lab2.entities.CartItem;
import org.springframework.stereotype.Controller;
import csd230.lab2.repositories.CartItemRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/cart-item")
public class CartItemController {

    CartItemRepository cartItemRepository;
    public CartItemController(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @GetMapping
    public String cartItems(Model model) {
        model.addAttribute("cartItems", cartItemRepository.findAll());
        return "cart-item/cart-item";
    }

    @GetMapping("/add-cart-item")
    public String cartItemForm(Model model) {
        model.addAttribute("cartItem", new CartItem());
        return "cart-item/add-cart-item";
    }

    @PostMapping("/add-cart-item")
    public String cartItemSubmit(@ModelAttribute CartItem cartItem, Model model) {
        model.addAttribute("cartItem", cartItem);
        cartItemRepository.save(cartItem);
        model.addAttribute("cartItems", cartItemRepository.findAll());
        return "redirect:/cart-item";
    }

//    @GetMapping("/edit-cart-item")
//    public String edit_cartItem(@RequestParam(value = "id", required = false) Integer id, Model model) {
//        Optional<CartItem> cartItem = cartItemRepository.findById(id.longValue());
//        model.addAttribute("cartItem", cartItem);
//        return "cart-item/edit-cart-item";
//    }

    @PostMapping("/remove-cart-item")
    public String remove_cartItem(@RequestParam(value = "id", required = false) Integer id, Model model) {
        cartItemRepository.deleteById(id.longValue());
        return "redirect:/cart-item";
    }
}
