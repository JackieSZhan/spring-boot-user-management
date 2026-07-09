package com.example.demo.service;


import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;

import java.util.List;

public interface BookService {
    BookResponseDto createBook(BookRequestDto request);

    BookResponseDto getBookById(Long id);

    List<BookResponseDto> getAllBooks();

    List<BookResponseDto> searchByTitle(String keyword);

    List<BookResponseDto> getLowStockBooks(int threshold);

    void deleteBook(Long id);

    BookResponseDto updateBook(Long id, BookRequestDto request);



}
