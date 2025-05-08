package com.company.docflow.repository;

import com.company.docflow.dto.DocumentDto;
import com.company.docflow.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUserLogin_userLogin(String userLogin);

    List<Document> findByCreationDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    List<Document> findByUserLogin_userLoginAndSignDateIsNotNull(String username);

    List<Document> findByUserLogin_userLoginAndSignDateIsNull(String username);
}