package com.company.docflow.controller;

import com.company.docflow.dto.DocumentDto;
import com.company.docflow.dto.DocumentUpdateDto;
import com.company.docflow.model.DocumentType;
import com.company.docflow.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<DocumentDto> createDocument(@RequestBody DocumentDto document) {
        DocumentDto createdDocument = documentService.createDocument(document);
        return new ResponseEntity<>(createdDocument, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DocumentDto>> getAllDocuments() {
        List<DocumentDto> documents = documentService.getAllDocuments();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentDto> updateDocument(@PathVariable Long id,
                                                   @RequestBody DocumentUpdateDto updateDto) {
        DocumentDto updatedDocument = documentService.updateDocument(id, updateDto);
        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/byLogin")
    public ResponseEntity<List<DocumentDto>> getDocumentsByUser(@RequestParam String username) {
        List<DocumentDto> documents = documentService.getDocumentsByUsername(username);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/search/byLoginAndIsSigned")
    public ResponseEntity<List<DocumentDto>> getDocumentsByUserAndSignStatus(
            @RequestParam String login,
            @RequestParam boolean isSigned) {
        List<DocumentDto> documents = documentService.getDocumentsByUsernameAndSignStatus(login, isSigned);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<DocumentDto>> getDocumentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999999999);

        List<DocumentDto> documents = documentService.getDocumentsByDateRange(fromDateTime, toDateTime);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/types")
    public ResponseEntity<DocumentType[]> getDocumentsTypes() {

        return ResponseEntity.ok(DocumentType.values());
    }

}