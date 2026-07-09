package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // --- ISBN should be unique, so we expect at most one result -> Optional<Book>
    Optional<Book> findByIsbn(String isbn);

    // --- "findByTitleContainingIgnoreCase" generates a case-insensitive LIKE query:
    //     SELECT * FROM books WHERE LOWER(title) LIKE LOWER('%?%')
    List<Book> findByTitleContainingIgnoreCase(String keyword);

    // --- Query derivation: Spring reads "findByAuthor" and generates:
    //     SELECT * FROM books WHERE author = ?
    List<Book> findByAuthor(String author);

    // --- "findByPriceLessThan" generates:
    //     SELECT * FROM books WHERE price < ?
    List<Book> findByPriceLessThan(Double maxPrice);

    // --- A custom JPQL query for something method-name derivation can't express cleanly:
    //     "find all books that are low in stock" (below a given threshold)
    @Query("SELECT b FROM Book b WHERE b.stockQuantity < :threshold")
    List<Book> findLowStockBooks(@Param("threshold") int threshold);

    // --- Example of a native SQL query (rarely needed, but good to know it's possible)
    // for anything too complex for name drivation, you can write sql in Query annotation
    @Query(value = "SELECT * FROM books ORDER BY price DESC LIMIT 1", nativeQuery = true)
    Optional<Book> findMostExpensiveBook();
}
