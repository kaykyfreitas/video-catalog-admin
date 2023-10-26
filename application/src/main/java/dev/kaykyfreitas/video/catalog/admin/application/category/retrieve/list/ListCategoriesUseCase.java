package dev.kaykyfreitas.video.catalog.admin.application.category.retrieve.list;

import dev.kaykyfreitas.video.catalog.admin.application.UseCase;
import dev.kaykyfreitas.video.catalog.admin.domain.category.CategorySearchQuery;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
