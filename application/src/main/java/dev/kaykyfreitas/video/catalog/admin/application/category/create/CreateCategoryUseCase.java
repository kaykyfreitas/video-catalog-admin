package dev.kaykyfreitas.video.catalog.admin.application.category.create;

import dev.kaykyfreitas.video.catalog.admin.application.UseCase;
import dev.kaykyfreitas.video.catalog.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
