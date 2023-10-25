package dev.kaykyfreitas.video.catalog.admin.domain.category;

public record CategorySearchQuery(
        int ṕage,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
