package com.example.demo.service;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.entity.Book;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**&
 * transactional annotation:
 * it wraps the entire method in a single database transaction
 *  if the method completes normally, the transaction is committed
 *
 *  if any unhandled exception is thrown, the transaction is automatically rolled back - nothing will be changed.
 *
 *
 *  readOnly = true, since transactional does a lot of things you couldn 't image
 *  with readOnly = true,  you will skip some operations in transactional method
 *  which you will have better performance
 *  this readOnly = true can only be used some methods that are only read the database not update data.
 *  
 *
 */
@Service
// if you can to do @Component annotation, you can change
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    @Override
    @Transactional
    public BookResponseDto createBook(BookRequestDto request) {
        bookRepository.findByIsbn(request.getIsbn()).ifPresent( existing ->{
            throw new DuplicateResourceException(
                    "A book with ISBN " + request.getIsbn() + " already exists"
            );
        });

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .publishedDate(request.getPublishedDate())
                .stockQuantity(request.getStockQuantity())
                .price(request.getPrice())
                .build();
        Book saved = bookRepository.save(book);
        return toResponseDto(saved);

    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDto getBookById(Long id){
        Book book  = findBookOrThrow(id);
        return  toResponseDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream().map(this::toResponseDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> searchByTitle(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getLowStockBooks(int threshold) {
        return bookRepository.findLowStockBooks(threshold).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        Book book = findBookOrThrow(id);
        bookRepository.delete(book);
    }

    @Override
    @Transactional
    public BookResponseDto updateBook(Long id, BookRequestDto request) {
        Book book = findBookOrThrow(id);

        // If the ISBN is being changed, re-check uniqueness against OTHER books
        if (!book.getIsbn().equals(request.getIsbn())) {
            bookRepository.findByIsbn(request.getIsbn()).ifPresent(existing -> {
                throw new DuplicateResourceException(
                        "A book with ISBN " + request.getIsbn() + " already exists");
            });
        }

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublishedDate(request.getPublishedDate());
        book.setStockQuantity(request.getStockQuantity());
        book.setPrice(request.getPrice());
        Book updated = bookRepository.save(book);
        return toResponseDto(updated);
    }


    private Book findBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    private BookResponseDto toResponseDto(Book book) {
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publishedDate(book.getPublishedDate())
                .stockQuantity(book.getStockQuantity())
                .price(book.getPrice())
                .build();
    }
}
