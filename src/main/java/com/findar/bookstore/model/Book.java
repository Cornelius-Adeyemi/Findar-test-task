package com.findar.bookstore.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.findar.bookstore.enum_package.BookStatus;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Book extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true, nullable = false)
    private String bookId;

    @Column(unique = true, nullable = false)
    private String bookTitle;

    @Lob
    @Column(length = 2000)
    private String bookDescription;

    @Enumerated(value = EnumType.STRING)
    private BookStatus bookStatus;

    private Long bookQuantity;


}
