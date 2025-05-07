package com.company.docflow.repository;

import com.company.docflow.model.Document;
import com.company.docflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Set<Document> findByUserLogin_userLogin(String userLogin);
//    List<Document> findByUsernameAndSignDateIsNotNull(String username);
//
//    List<Document> findByUsernameAndSignDateIsNull(String username);
//
//    List<Document> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}