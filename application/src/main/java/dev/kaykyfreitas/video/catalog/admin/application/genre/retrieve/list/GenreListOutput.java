package dev.kaykyfreitas.video.catalog.admin.application.genre.retrieve.list;

import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryId;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.Genre;

import java.time.Instant;
import java.util.List;

public record GenreListOutput(
        String name,
        boolean isActive,
        List<String> categories,
        Instant createdAt,
        Instant deletedAt
) {
    public static GenreListOutput from(final Genre aGenre) {
        return new GenreListOutput(
                aGenre.getName(),
                aGenre.isActive(),
                aGenre.getCategories().stream()
                        .map(CategoryId::getValue)
                        .toList(),
                aGenre.getCreatedAt(),
                aGenre.getDeletedAt()
        );
    }
}