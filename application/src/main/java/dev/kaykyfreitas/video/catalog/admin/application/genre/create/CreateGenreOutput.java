package dev.kaykyfreitas.video.catalog.admin.application.genre.create;

import dev.kaykyfreitas.video.catalog.admin.domain.genre.Genre;

public record CreateGenreOutput(
        String id
) {
    public static CreateGenreOutput from(final String anId) {
        return new CreateGenreOutput(anId);
    }

    public static CreateGenreOutput from(final Genre aGenre) {
        return new CreateGenreOutput(aGenre.getId().getValue());
    }
}
