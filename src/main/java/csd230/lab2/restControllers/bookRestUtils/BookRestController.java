package csd230.lab2.restControllers.bookRestUtils;

import csd230.lab2.entities.Book;
import csd230.lab2.repositories.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/book")
public class BookRestController {
    private final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping()
    List<Book> all() {
        return (List<Book>) bookRepository.findAll();
    }

    @GetMapping
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Book getBook(@PathVariable int id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new BookNotFoundException((long) id);
        }
        return book;
    }

    @PostMapping()
    Book newBook(@RequestBody Book newBook) {
        return bookRepository.save(newBook);
    }

    @PutMapping("/{id}")
    Book replaceBook(@RequestBody Book newBook, @PathVariable Long id) {

        return bookRepository.findById(id)
                .map(book -> {
                    book.setAuthor(newBook.getAuthor());
                    book.setIsbn(newBook.getIsbn());
                    book.setCopies(newBook.getCopies());
                    book.setDescription(newBook.getDescription());
                    book.setPrice(newBook.getPrice());
                    book.setTitle(newBook.getTitle());
                    book.setQuantity(newBook.getQuantity());

                    return bookRepository.save(book);
                })
                .orElseGet(() -> bookRepository.save(newBook));
    }
    @DeleteMapping("/{id}")
    void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}

