package dev.kaykyfreitas.video.catalog.admin.infrastructure.category.presenter;

import dev.kaykyfreitas.video.catalog.admin.application.category.retrieve.get.CategoryOutput;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.category.models.CategoryApiOutput;

public interface CategoryApiPresenter {
    static CategoryApiOutput present(final CategoryOutput output) {
        return new CategoryApiOutput(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}
