package dev.kaykyfreitas.video.catalog.admin.domain.genre;

import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.SearchQuery;

import java.util.Optional;

public interface GenreGateway {
    Genre create(Genre aGenre);
    void deleteById(GenreId anId);
    Optional<Genre> findById(GenreId anId);
    Genre update(Genre aGenre);
    Pagination<Genre> findAll(SearchQuery aQuery);
}
