package com.BookStore.controller;

import com.BookStore.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    // Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    // Get a book by ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Create a new book
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        books.add(book);
        return book;
    }

    // Update an existing book
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        for (int i = 0; i < books.size(); i++) {
            Book existingBook = books.get(i);
            if (existingBook.getId().equals(id)) {
                book.setId(id);
                books.set(i, book);
                return book;
            }
        }
        return null;
    }

    // Delete a book
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }

    // Filter books by title and author
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam Optional<String> title, @RequestParam Optional<String> author) {
        List<Book> filteredBooks = new ArrayList<>(books);

        title.ifPresent(t -> filteredBooks.removeIf(book -> !book.getTitle().contains(t)));
        author.ifPresent(a -> filteredBooks.removeIf(book -> !book.getAuthor().contains(a)));

        return filteredBooks;
    }
}
