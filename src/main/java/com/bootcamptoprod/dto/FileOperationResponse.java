package com.bootcamptoprod.dto;

public record FileOperationResponse(
        FileMetadata fileMetadata,
        String message,
        boolean success,
        String operationType
) {
}