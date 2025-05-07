package com.company.docflow.dto;

import com.company.docflow.model.DocumentType;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class DocumentCreateDto {
    private String name;
    private String type;
    private String body;
    private LocalDateTime creationDate;
    private LocalDateTime signDate;
    private String username;
}
