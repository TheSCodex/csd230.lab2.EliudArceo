package csd230.lab2;

import csd230.lab2.entities.Book;
import csd230.lab2.entities.Cart;
import csd230.lab2.entities.CartItem;
import csd230.lab2.repositories.BookRepository;
import csd230.lab2.repositories.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartRepository cartRepository;

    /*
     The tutorial I found recommended using H2Driver for testing because it's an in-memory database.
    */

    @Test
    void findAllBooks() {
        bookRepository.deleteAll();

        Book book1 = new Book(19.99, 2, "Book One", "Java Basics", 100, "Author One", "1234567890");
        Book book2 = new Book(29.99, 5, "Book Two", "Advanced Java", 200, "Author Two", "0987654321");
        bookRepository.save(book1);
        bookRepository.save(book2);

        Iterable<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(2);

        assertThat(books).anyMatch(book -> book.getTitle().equals("Java Basics"));
        assertThat(books).anyMatch(book -> book.getTitle().equals("Advanced Java"));
    }

    @Test
    void findBookById() {
        Book book = new Book(19.99, 2, "A great book", "Java Programming", 100, "Author Name", "1234567890");
        bookRepository.save(book);

        Optional<Book> foundBook = bookRepository.findById(book.getId());
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Java Programming");
        assertThat(foundBook.get().getAuthor()).isEqualTo("Author Name");
        assertThat(foundBook.get().getPrice()).isEqualTo(19.99);
    }

    @Test
    void testAddBookToCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);

        Book book = new Book(19.99, 2, "A great book", "Java Programming", 100, "Author Name", "1234567890");
        cart.addItem(book);
        cartRepository.save(cart);

        Optional<Cart> savedCart = cartRepository.findById(cart.getId());
        assertThat(savedCart).isPresent();
        assertThat(savedCart.get().getItems()).hasSize(1);

        CartItem savedItem = savedCart.get().getItems().iterator().next();
        assertThat(savedItem).isInstanceOf(Book.class);

        Book savedBook = (Book) savedItem;
        assertThat(savedBook.getTitle()).isEqualTo("Java Programming");
        assertThat(savedBook.getAuthor()).isEqualTo("Author Name");
    }

    @Test
    void testRemoveBookFromCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);

        Book book = new Book(19.99, 2, "A great book", "Java Programming", 100, "Author Name", "1234567890");
        bookRepository.save(book);

        cart.addItem(book);
        cartRepository.save(cart);

        cart.getItems().remove(book);
        book.setCart(null);
        cartRepository.save(cart);

        Optional<Cart> updatedCart = cartRepository.findById(cart.getId());
        assertThat(updatedCart).isPresent();
        assertThat(updatedCart.get().getItems()).isEmpty();
    }


    @Test
    void testUpdateBookDetailsInCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);

        Book book = new Book(19.99, 2, "A great book", "Java Programming", 100, "Author Name", "1234567890");
        bookRepository.save(book);

        cart.addItem(book);
        cartRepository.save(cart);

        book.setTitle("Advanced Java Programming");
        book.setPrice(24.99);
        bookRepository.save(book);

        Optional<Cart> updatedCart = cartRepository.findById(cart.getId());
        assertThat(updatedCart).isPresent();
        Book updatedBook = (Book) updatedCart.get().getItems().iterator().next();
        assertThat(updatedBook.getTitle()).isEqualTo("Advanced Java Programming");
        assertThat(updatedBook.getPrice()).isEqualTo(24.99);
    }

}
