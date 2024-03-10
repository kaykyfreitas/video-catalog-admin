package dev.kaykyfreitas.video.catalog.admin.application.video.retrieve.list;

import dev.kaykyfreitas.video.catalog.admin.domain.video.Video;

import java.time.Instant;

public record VideoListOutput(
        String id,
        String title,
        String description,
        Instant createdAT,
        Instant updatedAt
) {
    public static VideoListOutput from(final Video aVideo) {
        return new VideoListOutput(
                aVideo.getId().getValue(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt()
        );
    }
}
