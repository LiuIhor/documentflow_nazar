package com.company.docflow.dto;

import lombok.Data;

import java.time.LocalDateTime;


public record DocumentDto(String name, String type, String body,
                          LocalDateTime creationDate, LocalDateTime signDate,
                          String userLogin) {
}
