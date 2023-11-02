package dev.kaykyfreitas.video.catalog.admin.infrastructure.api.controllers;

import dev.kaykyfreitas.video.catalog.admin.application.category.create.CreateCategoryCommand;
import dev.kaykyfreitas.video.catalog.admin.application.category.create.CreateCategoryOutput;
import dev.kaykyfreitas.video.catalog.admin.application.category.create.CreateCategoryUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.category.update.UpdateCategoryCommand;
import dev.kaykyfreitas.video.catalog.admin.application.category.update.UpdateCategoryOutput;
import dev.kaykyfreitas.video.catalog.admin.application.category.update.UpdateCategoryUseCase;
import dev.kaykyfreitas.video.catalog.admin.domain.category.CategorySearchQuery;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;
import dev.kaykyfreitas.video.catalog.admin.domain.validation.handler.Notification;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.api.CategoryAPI;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.category.models.CategoryListResponse;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.category.models.CategoryResponse;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.category.models.CreateCategoryRequest;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.category.models.UpdateCategoryRequest;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.category.presenter.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(
            final CreateCategoryUseCase createCategoryUseCase,
            final GetCategoryByIdUseCase getCategoryByIdUseCase,
            final UpdateCategoryUseCase updateCategoryUseCase,
            final DeleteCategoryUseCase deleteCategoryUseCase,
            final ListCategoriesUseCase listCategoriesUseCase
    ) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
        this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryRequest input) {
        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                Objects.nonNull(input.active()) ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);

        return this.createCategoryUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public Pagination<CategoryListResponse> createCategory(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return listCategoriesUseCase
                .execute(new CategorySearchQuery(page, perPage, search, sort, direction))
                .map(CategoryApiPresenter::present);
    }

    @Override
    public CategoryResponse getById(final String anId) {
        return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(anId));
    }

    @Override
    public ResponseEntity<?> updateById(final String anId, final UpdateCategoryRequest input) {
        final var aCommand = UpdateCategoryCommand.with(
                anId,
                input.name(),
                input.description(),
                Objects.nonNull(input.active()) ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final String anId) {
        this.deleteCategoryUseCase.execute(anId);
    }

}
