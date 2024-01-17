package com.findar.bookstore.controller.book.user;


import com.findar.bookstore.controller_advice.ZeusException;
import com.findar.bookstore.service.book_service.BookManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/book-usage")
@Tag(name="User book management")
public class UserBookManagementController {

    private final BookManagementService bookManagementService;

    @Operation(summary = "An end point to rent a book")
    @GetMapping("/rent-book/{bookId}")
    public ResponseEntity<Object>  rentBook(@PathVariable("bookId") String bookId) throws ZeusException {

        return ResponseEntity.ok(bookManagementService.borrowBook(bookId));

    }
    @Operation(summary = "An end point to return a book")
    @GetMapping("/return-book/{bookId}")
    public ResponseEntity<Object> returnBook(@PathVariable("bookId") String bookId) throws ZeusException {

        return ResponseEntity.ok(bookManagementService.returnBook(bookId));
    }
    @Operation(summary = "An end point to get all books")
    @GetMapping("/get-all-books")
    public ResponseEntity<Object> getAllBook() throws ZeusException {


        return ResponseEntity.ok(bookManagementService.getAllBooks());
    }
    @Operation(summary = "An end point to get a specific book details ")
    @GetMapping("/get-a-book/{bookId}")
    public ResponseEntity<Object> getABook(@PathVariable("bookId") String bookId) throws ZeusException {


        return ResponseEntity.ok(bookManagementService.getABook(bookId));
    }




}
