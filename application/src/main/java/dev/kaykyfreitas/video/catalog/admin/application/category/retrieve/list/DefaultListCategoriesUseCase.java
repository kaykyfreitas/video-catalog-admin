package dev.kaykyfreitas.video.catalog.admin.application.category.retrieve.list;

import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.SearchQuery;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultListCategoriesUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final SearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery)
                .map(CategoryListOutput::from);
    }
}
