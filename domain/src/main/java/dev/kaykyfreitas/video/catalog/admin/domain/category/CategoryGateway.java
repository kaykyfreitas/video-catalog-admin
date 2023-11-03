package dev.kaykyfreitas.video.catalog.admin.domain.category;

import dev.kaykyfreitas.video.catalog.admin.domain.pagination.SearchQuery;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {
    Category create(Category aCategory);
    void deleteById(CategoryId anId);
    Optional<Category> findById(CategoryId anId);
    Category update(Category aCategory);
    Pagination<Category> findAll(SearchQuery aQuery);
}
