package com.company.docflow.dto;

import com.company.docflow.model.DocumentType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DocumentUpdateDto {
    private String name;
    private DocumentType type;
    private String body;
    private LocalDateTime signDate;
    private String username;
}