package com.company.docflow.repository;

import com.company.docflow.model.Document;
import com.company.docflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserLogin(String userLogin);
}
