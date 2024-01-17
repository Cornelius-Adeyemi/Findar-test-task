package com.findar.bookstore.service.book_service;

import com.findar.bookstore.controller_advice.ZeusException;
import com.findar.bookstore.dto.bookDTO.BookRequest;
import com.findar.bookstore.dto.bookDTO.UpdateBookDTO;
import com.findar.bookstore.dto.responseObject.SuccessfulResponse;
import com.findar.bookstore.enum_package.BookStatus;
import com.findar.bookstore.enum_package.Role;
import com.findar.bookstore.model.Book;
import com.findar.bookstore.model.RootUser;
import com.findar.bookstore.repository.BookRepo;
import com.findar.bookstore.repository.BorrowTableRepo;
import com.findar.bookstore.repository.RootUserRepo;
import com.findar.bookstore.util.GetLoginUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookManagementServiceTest {


    @InjectMocks
    private BookManagementService bookManagementService;

    @Mock

    private  BookRepo bookRepo;

    @Mock

    private BorrowTableRepo borrowTableRepo;

    @Mock

    private GetLoginUser getLoginUser;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(BookManagementServiceTest.class);

    }



    @Test
    void createBook() throws ZeusException {
        // Arrange
        BookRequest bookRequest = new BookRequest();
        bookRequest.setBookTitle("Sample Book");
        bookRequest.setBookQuantity(5L);

        when(getLoginUser.checkForCurrentUserId()).thenReturn(createMockAdminUser());
        when(bookRepo.findByBookTitle(any())).thenReturn(Optional.empty());
        when(bookRepo.save(any())).thenReturn(createMockBook());

        // Act
        SuccessfulResponse result = (SuccessfulResponse) bookManagementService.createBook(bookRequest);

        // Assert
        verify(bookRepo, times(1)).save(any());
        assertEquals(bookRequest.getBookTitle(), ((Book)result.getData()).getBookTitle());

    }

    @Test
    void deleteBook() throws ZeusException {
        String bookId = "BK_123";

        when(getLoginUser.checkForCurrentUserId()).thenReturn(createMockAdminUser());
        when(bookRepo.findByBookId(any())).thenReturn(Optional.of(createMockBook()));

        // Act
        Object result = bookManagementService.deleteBook(bookId);

        // Assert
        verify(bookRepo, times(1)).deleteByBookId(any());
        assertEquals(SuccessfulResponse.class, result.getClass());
    }

    @Test
    void updateBook() throws ZeusException {
        // Arrange
        String bookId = "BK_123";
        UpdateBookDTO updateBookDTO = new UpdateBookDTO();
        updateBookDTO.setBookQuantity(10L);

        when(getLoginUser.checkForCurrentUserId()).thenReturn(createMockAdminUser());
        when(bookRepo.findByBookId(any())).thenReturn(Optional.of(createMockBook()));
        when(bookRepo.save(any())).thenReturn(createMockBook());

        // Act
        Object result = bookManagementService.updateBook(bookId, updateBookDTO);

        // Assert
        verify(bookRepo, times(1)).save(any());
        assertEquals(SuccessfulResponse.class, result.getClass());
    }



    @Test
    void borrowBook() {
    }

    @Test
    void returnBook() {
    }

    private RootUser createMockAdminUser() {
        RootUser user = new RootUser();
        user.setRole(Role.ADMIN);
        return user;
    }

    private Book createMockBook() {
        Book book = new Book();
        book.setBookId("BK_123");
        book.setBookTitle("Sample Book");
        book.setBookQuantity(5L);
        book.setBookStatus(BookStatus.IN_STOCK);
        return book;
    }
}