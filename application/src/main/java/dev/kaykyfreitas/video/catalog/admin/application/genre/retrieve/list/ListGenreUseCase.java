package dev.kaykyfreitas.video.catalog.admin.application.genre.retrieve.list;

import dev.kaykyfreitas.video.catalog.admin.application.UseCase;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.SearchQuery;

public abstract class ListGenreUseCase extends UseCase<SearchQuery, Pagination<GenreListOutput>> {
}
