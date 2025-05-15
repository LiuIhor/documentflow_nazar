package com.company.docflow.dto;

import java.time.LocalDateTime;

public record ErrorDto(LocalDateTime dateTime, String title, String message) {
}
