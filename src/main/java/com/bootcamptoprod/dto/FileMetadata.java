package com.bootcamptoprod.dto;

import java.time.Instant;

public record FileMetadata(
        String fileName,
        String exactPath,
        long fileSize,
        Instant createdAt,
        Instant updatedAt,
        String content,
        boolean isDeleted,
        String error
) {
}