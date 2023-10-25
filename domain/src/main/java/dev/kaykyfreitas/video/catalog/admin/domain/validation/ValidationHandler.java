package dev.kaykyfreitas.video.catalog.admin.domain.validation;

import java.util.List;
import java.util.Objects;

public interface ValidationHandler {
    ValidationHandler append(Error anError);
    ValidationHandler append(ValidationHandler aHandler);
    ValidationHandler validate(Validation aValidation);
    List<Error> getErrors();

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        return (Objects.nonNull(getErrors()) && !getErrors().isEmpty()) ?  getErrors().get(0) : null;
    }

    interface Validation {
        void validate();
    }
}
