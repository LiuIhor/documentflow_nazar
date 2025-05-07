package com.company.docflow.controller;

import com.company.docflow.dto.DocumentCreateDto;
import com.company.docflow.dto.DocumentDto;
import com.company.docflow.dto.DocumentUpdateDto;
import com.company.docflow.model.Document;
import com.company.docflow.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        Document createdDocument = documentService.createDocument(document);
        return new ResponseEntity<>(createdDocument, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DocumentDto>> getAllDocuments() {
        List<DocumentDto> documents = documentService.getAllDocuments();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id,
                                                   @RequestBody DocumentUpdateDto updateDto) {
        Document updatedDocument = documentService.updateDocument(id, updateDto);
        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/user/{username}")
//    public ResponseEntity<List<Document>> getDocumentsByUser(@PathVariable String username) {
//        List<Document> documents = documentService.getDocumentsByUsername(username);
//        return ResponseEntity.ok(documents);
//    }

//    @GetMapping("/user/{username}/signed/{isSigned}")
//    public ResponseEntity<List<Document>> getDocumentsByUserAndSignStatus(
//            @PathVariable String username,
//            @PathVariable boolean isSigned) {
//        List<Document> documents = documentService.getDocumentsByUsernameAndSignStatus(username, isSigned);
//        return ResponseEntity.ok(documents);
//    }

//    @GetMapping("/date-range")
//    public ResponseEntity<List<Document>> getDocumentsByDateRange(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
//        List<Document> documents = documentService.getDocumentsByDateRange(startDate, endDate);
//        return ResponseEntity.ok(documents);
//    }
}