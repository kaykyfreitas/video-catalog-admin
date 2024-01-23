package dev.kaykyfreitas.video.catalog.admin.application.genre.retrieve.list;

import dev.kaykyfreitas.video.catalog.admin.domain.genre.GenreGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListGenreUseCase extends ListGenreUseCase {

    private final GenreGateway genreGateway;

    public DefaultListGenreUseCase(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public Pagination<GenreListOutput> execute(final SearchQuery aQUery) {
        return this.genreGateway.findAll(aQUery)
                .map(GenreListOutput::from);
    }
}
