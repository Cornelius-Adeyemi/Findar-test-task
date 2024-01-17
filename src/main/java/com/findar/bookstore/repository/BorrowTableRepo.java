package com.findar.bookstore.repository;

import com.findar.bookstore.enum_package.BorrowedStatus;
import com.findar.bookstore.model.BorrowedTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowTableRepo extends JpaRepository<BorrowedTable,Long> {

    Optional<BorrowedTable> findByEmailAndBorrowedStatus(String email, BorrowedStatus borrowedStatus);

    Optional<BorrowedTable>  findByEmailAndBookIdAndBorrowedStatus(String email, String bookId, BorrowedStatus borrowedStatus);
}
