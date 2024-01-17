package com.findar.bookstore.repository;

import com.findar.bookstore.model.RootUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RootUserRepo extends JpaRepository<RootUser, Long> {

    Optional<RootUser> findByEmail(String email);
}
