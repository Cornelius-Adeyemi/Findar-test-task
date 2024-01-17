package com.findar.bookstore.service.book_service;


import com.findar.bookstore.controller_advice.ZeusException;
import com.findar.bookstore.dto.bookDTO.BookRequest;
import com.findar.bookstore.dto.bookDTO.UpdateBookDTO;
import com.findar.bookstore.dto.responseObject.SuccessfulResponse;
import com.findar.bookstore.enum_package.BookStatus;
import com.findar.bookstore.enum_package.BorrowedStatus;
import com.findar.bookstore.enum_package.Role;
import com.findar.bookstore.model.Book;
import com.findar.bookstore.model.BorrowedTable;
import com.findar.bookstore.model.RootUser;
import com.findar.bookstore.repository.BookRepo;
import com.findar.bookstore.repository.BorrowTableRepo;
import com.findar.bookstore.repository.RootUserRepo;
import com.findar.bookstore.util.GetLoginUser;
import com.findar.bookstore.util.RandStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookManagementService {

 private final BookRepo bookRepo;

 private final RootUserRepo rootUserRepo;

 private final BorrowTableRepo borrowTableRepo;

 private final GetLoginUser getLoginUser;

    /**
     * method to add book to book table. check DTO structure through swagger UI
     * @param bookRequest
     * @return
     * @throws ZeusException
     */
    public Object createBook(BookRequest bookRequest) throws ZeusException {
      //get login user
        RootUser principal = getLoginUser.checkForCurrentUserId();

        //check if user has admin role
        if(!principal.getRole().toString().equals(Role.ADMIN.toString())){
            throw new ZeusException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.toString(), "You don't have access to this action");
        }
       //check if book title exist
        Optional<Book> searchBook = bookRepo.findByBookTitle(bookRequest.getBookTitle());
        if(searchBook.isPresent()){
            throw new ZeusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), "Book title aready exist");
        }
        //if the quantity of book is greater than zero
        if(bookRequest.getBookQuantity().intValue() <= 0){
            throw new ZeusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), "Book quantity must be greater than zero");
        }
       //create random book id
        String bookId=  "BK_"+ RandStringUtil.generateRandomString(7);

        Book book = new Book();

        BeanUtils.copyProperties(bookRequest,book );

        book.setBookId(bookId);
        //set book status
        book.setBookStatus(BookStatus.IN_STOCK);

        Book book1;

        try{
           book1 = bookRepo.save(book);


        }catch (Exception e){
            throw new ZeusException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Error saving to database, try again");
        }


         return new SuccessfulResponse<Object>(book1);


    }

    public Object deleteBook(String bookId ) throws ZeusException {

        RootUser principal = getLoginUser.checkForCurrentUserId();
       //check if user has the the admin role
        if(!principal.getRole().toString().equals(Role.ADMIN.toString())){
            throw new ZeusException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.toString(), "You don't have access to this action");
        }
      // check if book with the provided bookId exist
        Optional<Book> searchBook = bookRepo.findByBookId(bookId);
        if(!searchBook.isPresent()){
            throw new ZeusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), "BookId doesn't exist");
        }

        try{
           bookRepo.deleteByBookId(bookId);
        }catch(Exception e){
            throw new ZeusException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(),  "Error deleting from database, try again");
        }

        return new SuccessfulResponse<Object>("Book successfully deleted");

    }


    public Object updateBook(String bookId, UpdateBookDTO updateBookDTO ) throws ZeusException {

        RootUser principal = getLoginUser.checkForCurrentUserId();
       //check if user has admin role
        if(!principal.getRole().toString().equals(Role.ADMIN.toString())){
            throw new ZeusException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.toString(), "You don't have access to this action");
        }
       //check if book is present
        Optional<Book> searchBook = bookRepo.findByBookId(bookId);
        if(!searchBook.isPresent()){
            throw new ZeusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), "BookId doesn't exist");
        }

        Book book = searchBook.get();

        updateNecessaryField(book, updateBookDTO);

        Book book1;

        try{
            //update
          book1 =   bookRepo.save(book);
        }catch(Exception e){
            throw new ZeusException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(),  "Error updating, try again");
        }

        return new SuccessfulResponse<Object>(book1);

    }


    private void updateNecessaryField(Book book, UpdateBookDTO updateBookDTO){

        if(updateBookDTO.getBookQuantity() >= 0){
            book.setBookQuantity(updateBookDTO.getBookQuantity());
            book.setBookStatus(BookStatus.IN_STOCK);
        }

        if(updateBookDTO.getBookTitle() != null && !updateBookDTO.getBookTitle().isEmpty()){
            book.setBookTitle(updateBookDTO.getBookTitle());
        }

        if(updateBookDTO.getBookDescription() !=null && !updateBookDTO.getBookDescription().isEmpty()){
            book.setBookDescription(updateBookDTO.getBookDescription());
        }

    }


    public Object getAllBooks() throws ZeusException {
       // the response is not paginated

        try {
            List<Book> bookList = bookRepo.findAll();
            Object response = bookList.isEmpty()? "book list is empty" : bookList;

            return new SuccessfulResponse<Object>(response);
        }catch (Exception e){
            throw new ZeusException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(),  "Error updating, try again");
        }
    }

    public Object getABook(String bookId) throws ZeusException {



            Book book = bookRepo.findByBookId(bookId).orElseThrow(()->{
                return   new ZeusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), "Invalid book id");
            });


            return new SuccessfulResponse<Object>(book);

    }

    public Object borrowBook(String bookId) throws ZeusException {
       //get login user
        RootUser principal = getLoginUser.checkForCurrentUserId();

        //check if user has unreturned book
        Optional<BorrowedTable> borrowedTable = borrowTableRepo.findByEmailAndBorrowedStatus(principal.getEmail(), BorrowedStatus.NOT_RETURN);

        if(borrowedTable.isPresent()){
            throw new ZeusException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.toString(),  "You still have a book you are yet to return ");

        }

        //check if the book the user want to borrow exist
        Book searchBook = bookRepo.findByBookId(bookId).orElseThrow(()-> {
            return   new ZeusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), "Invalid book id");
        });

        Long bookQuantity = searchBook.getBookQuantity();
        //check if book is out od stock
        if(bookQuantity ==0){
            throw new ZeusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),  "Book is out of stock");
        }else if(bookQuantity >=0){ // reduce the number of quantity if the book is in stock
            bookQuantity = bookQuantity - 1;
        }
        searchBook.setBookQuantity(bookQuantity);
        if(bookQuantity == 0){// if the quantity si zero after removing, change the book status to out of stock
            searchBook.setBookStatus(BookStatus.OUT_OF_STOCK);
        }

        try {
            //update and save
            bookRepo.save(searchBook);
        }catch(Exception e){
            throw new ZeusException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(),  "Error saving to database");
        }
       //save in the borrow table to keep track of user
        BorrowedTable borrowedTable1 = BorrowedTable.builder()
                .email(principal.getEmail())
                .bookId(searchBook.getBookId())
                .borrowedStatus(BorrowedStatus.NOT_RETURN)
                .build();


        try {
            borrowTableRepo.save(borrowedTable1);
        }catch(Exception e){
            throw new ZeusException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(),  "Error saving to database");
        }


        return new SuccessfulResponse<Object>("Successfully borrow book with book id "+ searchBook.getBookId());


    }

    @Transactional
    public Object returnBook(String bookId) throws ZeusException {
       //get login user
        RootUser principal = getLoginUser.checkForCurrentUserId();
        //check if user has an unreturn book with that Id
        Optional<BorrowedTable> borrowedTable = borrowTableRepo.findByEmailAndBookIdAndBorrowedStatus(principal.getEmail(), bookId, BorrowedStatus.NOT_RETURN);

        if(!borrowedTable.isPresent()){
            throw new ZeusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),  "you've not borrowed any book with the book id "+ bookId);

        }
       //find book in the book db
        Book searchBook = bookRepo.findByBookId(bookId).orElseThrow(()-> {
            return   new ZeusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), "Invalid book id");
        });
        //set status to return in the borrowed table
        BorrowedTable borrowedTable1 = borrowedTable.get();
        borrowedTable1.setBorrowedStatus(BorrowedStatus.RETURNED);
        borrowTableRepo.save(borrowedTable1);

        Long bookQuantity = searchBook.getBookQuantity() + 1;

        searchBook.setBookQuantity(bookQuantity);
        searchBook.setBookStatus(BookStatus.IN_STOCK);


        try{

            bookRepo.save(searchBook);
        }catch(Exception e){
            throw new ZeusException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(),  "Error updating, try again");
        }


        return new SuccessfulResponse<Object>("Book successfully returned");



    }



    }




