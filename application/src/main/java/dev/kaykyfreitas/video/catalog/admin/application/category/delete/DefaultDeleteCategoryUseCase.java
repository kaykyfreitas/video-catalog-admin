package dev.kaykyfreitas.video.catalog.admin.application.category.delete;

import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryId;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public void execute(String anId) {
        this.categoryGateway.deleteById(CategoryId.from(anId));
    }
}
