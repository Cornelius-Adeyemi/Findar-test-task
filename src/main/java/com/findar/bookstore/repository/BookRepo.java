package com.findar.bookstore.repository;

import com.findar.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookRepo extends JpaRepository<Book,Long> {

    Optional<Book> findByBookTitle(String bookTitle);

    @Modifying
    @Transactional
    void deleteByBookId(String bookId);

    Optional<Book> findByBookId(String bookId);
}
