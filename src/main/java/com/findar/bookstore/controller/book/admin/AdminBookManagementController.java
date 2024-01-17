package com.findar.bookstore.controller.book.admin;


import com.findar.bookstore.controller_advice.ZeusException;
import com.findar.bookstore.dto.bookDTO.BookRequest;
import com.findar.bookstore.dto.bookDTO.UpdateBookDTO;
import com.findar.bookstore.service.book_service.BookManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/management")
@Tag(name="Admin book management")
public class AdminBookManagementController {

    private final BookManagementService bookManagementService;

    @Operation(summary = "An end point to add a book")
   @PostMapping("/add-book")
    public ResponseEntity<Object> addbook(@Valid @RequestBody BookRequest bookRequest) throws ZeusException {

        return ResponseEntity.ok(bookManagementService.createBook(bookRequest));


    }
    @Operation(summary = "An end point to delete a book")
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<Object> deleteBook(@PathVariable("bookId") String bookId) throws ZeusException {

        return ResponseEntity.ok(bookManagementService.deleteBook(bookId));

    }
    @Operation(summary = "An end point to update a book")
    @PatchMapping("/update/{bookId}")
    public ResponseEntity<Object> updateBookDetails(@PathVariable("bookId") String bookId, @RequestBody UpdateBookDTO updateBookDTO) throws ZeusException {

        return ResponseEntity.ok(bookManagementService.updateBook(bookId, updateBookDTO));
    }
}
