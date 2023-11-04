package dev.kaykyfreitas.video.catalog.admin.domain.validation.handler;

import dev.kaykyfreitas.video.catalog.admin.domain.exceptions.DomainException;
import dev.kaykyfreitas.video.catalog.admin.domain.validation.Error;
import dev.kaykyfreitas.video.catalog.admin.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(final Error anError) {
        throw DomainException.with(anError);
    }

    @Override
    public ValidationHandler append(final ValidationHandler aHandler) {
        throw DomainException.with(aHandler.getErrors());
    }

    @Override
    public <T> T validate(final Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (final Exception e) {
            throw DomainException.with(new Error(e.getMessage()));
        }
    }

    @Override
    public List<Error> getErrors() {
        return null;
    }
}
