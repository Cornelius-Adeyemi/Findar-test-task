package com.findar.bookstore.model;


import com.findar.bookstore.enum_package.BorrowedStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowedTable  extends  Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String bookId;

    @Enumerated(value = EnumType.STRING)
    private BorrowedStatus borrowedStatus;
}
