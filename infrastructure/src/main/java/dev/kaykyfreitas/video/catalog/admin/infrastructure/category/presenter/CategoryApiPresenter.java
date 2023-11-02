package dev.kaykyfreitas.video.catalog.admin.infrastructure.category.presenter;

import dev.kaykyfreitas.video.catalog.admin.application.category.retrieve.get.CategoryOutput;
import dev.kaykyfreitas.video.catalog.admin.application.category.retrieve.list.CategoryListOutput;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.category.models.CategoryResponse;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.category.models.CategoryListResponse;

public interface CategoryApiPresenter {
    static CategoryResponse present(final CategoryOutput output) {
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
    static CategoryListResponse present(final CategoryListOutput output) {
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
