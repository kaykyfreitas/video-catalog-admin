package dev.kaykyfreitas.video.catalog.admin.application.category.create;

import dev.kaykyfreitas.video.catalog.admin.domain.category.Category;
import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryId;

public record CreateCategoryOutput(
        CategoryId id
) {
    public static CreateCategoryOutput from(final Category aCategory) {
        return new CreateCategoryOutput(aCategory.getId());
    }
}
