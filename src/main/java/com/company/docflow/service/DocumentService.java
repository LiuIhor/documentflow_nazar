package com.company.docflow.service;

import com.company.docflow.dto.DocumentDto;
import com.company.docflow.dto.DocumentUpdateDto;
import com.company.docflow.model.Document;
import com.company.docflow.model.DocumentType;
import com.company.docflow.model.User;
import com.company.docflow.repository.DocumentRepository;
import com.company.docflow.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public DocumentDto createDocument(DocumentDto document) {
        Document savedDocument = documentRepository.save(convertToEntity(document));
        return convertToDTO(savedDocument);
    }

    @Transactional
    public DocumentDto updateDocument(Long id, DocumentUpdateDto updateDto) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id: " + id));

        if (updateDto.getName() != null) {
            document.setName(updateDto.getName());
        }

        if (updateDto.getType() != null) {
            document.setType(updateDto.getType());
        }

        if (updateDto.getBody() != null) {
            document.setBody(updateDto.getBody());
        }

        if (updateDto.getSignDate() != null) {
            document.setSignDate(updateDto.getSignDate());
        }

        Document savedDocument = documentRepository.save(document);
        return convertToDTO(savedDocument);
    }

    @Transactional
    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new EntityNotFoundException("Document not found with id: " + id);
        }
        documentRepository.deleteById(id);
    }

    public List<DocumentDto> getDocumentsByUsername(String username) {
        List<Document> documents =  documentRepository.findByUserLogin_userLogin(username);
        return documents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DocumentDto> getDocumentsByDateRange(LocalDateTime fromDate, LocalDateTime toDate) {
        List<Document> documents = documentRepository.findByCreationDateBetween(fromDate, toDate);
        return documents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DocumentDto> getDocumentsByUsernameAndSignStatus(String username, boolean isSigned) {
        if (isSigned) {
            List<Document> foundDocuments = documentRepository.findByUserLogin_userLoginAndSignDateIsNotNull(username);
            return foundDocuments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            List<Document> foundDocuments = documentRepository.findByUserLogin_userLoginAndSignDateIsNull(username);
            return foundDocuments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

    public List<DocumentDto> getAllDocuments() {
        List<Document>  documents = documentRepository.findAll();
        return documents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }


    private DocumentDto convertToDTO(Document document) {
        return new DocumentDto(document.getId(), document.getName(), document.getType().name(), document.getBody(),
                document.getCreationDate(), document.getSignDate(), document.getUserLogin().getUserLogin());
    }

    private Document convertToEntity(DocumentDto documentDto) {

        User user = userRepository.findByUserLogin(documentDto.userLogin())
                .orElseThrow(() -> new RuntimeException("User not found: " + documentDto.userLogin()));

        Document document = new Document();
        document.setName(documentDto.name());
        document.setType(DocumentType.valueOf(documentDto.type()));
        document.setBody(documentDto.body());
        document.setCreationDate(documentDto.creationDate());
        document.setSignDate(documentDto.signDate());
        document.setUserLogin(user);
        return document;
    }
}