package com.BookStore.controller;

import com.BookStore.model.Book;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.BookStore.exception.ResourceNotFoundException;


@RestController
@RequestMapping("/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Get a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
    }

    // Create a new book
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        books.add(book);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/books/" + book.getId());
        
        return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
    }

    // Update an existing book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        for (int i = 0; i < books.size(); i++) {
            Book existingBook = books.get(i);
            if (existingBook.getId().equals(id)) {
                book.setId(id);
                books.set(i, book);
                return new ResponseEntity<>(book, HttpStatus.OK);
            }
        }
        throw new ResourceNotFoundException("Book with ID " + id + " not found");
    }

    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResourceNotFoundException("Book with ID " + id + " not found");
        }
    }

    // Filter books by title and author
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam Optional<String> title, @RequestParam Optional<String> author) {
        List<Book> filteredBooks = new ArrayList<>(books);

        title.ifPresent(t -> filteredBooks.removeIf(book -> !book.getTitle().contains(t)));
        author.ifPresent(a -> filteredBooks.removeIf(book -> !book.getAuthor().contains(a)));

        return new ResponseEntity<>(filteredBooks, HttpStatus.OK);
    }
}
